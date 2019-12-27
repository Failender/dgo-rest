package de.failender.dgo.rest.synchronization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.rest.helden.VersionService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.ezql.properties.PropertyReader;
import de.failender.heldensoftware.JaxbUtil;
import de.failender.heldensoftware.api.HeldenApi;
import de.failender.heldensoftware.api.RestUtils;
import de.failender.heldensoftware.api.requests.ConvertingRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldPdfRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldXmlRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import static de.failender.dgo.rest.helden.VersionService.extractLastEreignisString;

public class SynchronizationService {


    public static SynchronizationService INSTANCE;

    private final ObjectMapper objectMapper;
    private final Map<String, String> headers;
    private final String baseUrl;

    private static final TypeReference<List<DropdownData<Long>>> GRUPPEN_INFO = new TypeReference<List<DropdownData<Long>>>() {};

    private SynchronizationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        headers = new HashMap<>();
        String password = PropertyReader.getProperty("dsa.gruppen.online.user.password");
        String username = PropertyReader.getProperty("dsa.gruppen.online.user.name");
        baseUrl = PropertyReader.getProperty("dsa.gruppen.online.adress");

        headers.put("X-USER", username);
        headers.put("X-PASSWORD", password);
    }

    public static void intialize(ObjectMapper  objectMapper) {
        INSTANCE = new SynchronizationService(objectMapper);
        if (isSyncEnabled()) {
            INSTANCE.synchronize();
        }
    }

    private static boolean isSyncEnabled() {
        return "true".equals(PropertyReader.getProperty("dsa.gruppen.online.synchronize"));

    }

    public static void synchronizeForUser(UserEntity userEntity) {
        if (isSyncEnabled()) {
            HeldRepositoryService.findByUserId(userEntity.getId());

        }
    }

    public void synchronize() {
        doSynchronize(HeldRepositoryService.findAll());
    }

    private void doSynchronize(List<HeldEntity> heldEntities) {
        for (HeldEntity heldEntity : heldEntities) {
            try {
                List<DSOHeldVersion> versionen = getVersionenForHeld(heldEntity.getId());
                Set<Integer> versions =versionen
                        .stream()
                        .map(DSOHeldVersion::getVersion)
                        .collect(Collectors.toSet());
                if(versions.size() != versionen.size()){
                    System.err.println("Duplicate version found for held " + heldEntity.getId());
                    continue;
                }
                if(VersionRepositoryService.findLatestVersion(heldEntity).getVersion() != versionen.size()) {
                    System.out.println("Synchronizing held " + heldEntity.getId() + " " + heldEntity.getName());
                    VersionService.deleteAllVersions(heldEntity);

                    InputStream is = getXml(heldEntity.getId());
                    Path temp = Files.createTempFile(heldEntity.getId().toString(), ".zip");
                    FileUtils.copyInputStreamToFile(is, temp.toFile());
                    try {
                        ZipFile zf = new ZipFile(temp.toFile());
                        for (DSOHeldVersion dsoHeldVersion : versionen) {
                            createVersion(heldEntity, dsoHeldVersion, zf);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                System.err.println("Synchrnisation for held " + heldEntity.getName()  + " failed");
                e.printStackTrace();
            }
        }

    }


    private void createVersion(HeldEntity heldEntity, DSOHeldVersion version, ZipFile zipFile) throws IOException, JAXBException {
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setCacheId(UUID.randomUUID());
        versionEntity.setVersion(version.getVersion());
        versionEntity.setCreatedDate(version.getDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        versionEntity.setHeldid(heldEntity.getId());

        Daten daten = getDaten(heldEntity.getId(), version.getVersion());
        versionEntity.setLastEvent(extractLastEreignisString(daten.getEreignisse().getEreignis()));

        InputStream is = zipFile.getInputStream(zipFile.getEntry(version.getVersion() + ".xml"));
        versionEntity.setAp(daten.getAngaben().getAp().getGesamt().intValue());
        InputStream datenStream = JaxbUtil.marshall(daten);
        Beans.HELDEN_API.getCacheHandler().doCache(new ReturnHeldXmlRequest(heldEntity.getId(), null, versionEntity.getCacheId()), is);
        Beans.HELDEN_API.getCacheHandler().doCache(new ReturnHeldDatenWithEreignisseRequest(heldEntity.getId(), null, versionEntity.getCacheId()), datenStream);

        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(heldEntity.getId(), null, versionEntity.getCacheId())).block();
        InputStream pdfStream = Beans.HELDEN_API.request(new ConvertingRequest(HeldenApi.Format.pdfintern, xml)).block();
        Beans.HELDEN_API.getCacheHandler().doCache(new ReturnHeldPdfRequest(heldEntity.getId(), null, versionEntity.getCacheId()),pdfStream);

        VersionRepositoryService.saveVersion(heldEntity, versionEntity);
        System.out.println("Version " + version.getVersion() + " for held " + heldEntity.getId() + " created");

    }





    private List<DSOHeldVersion> getVersionenForHeld(Long gruppe) {
        InputStream is = RestUtils.request(baseUrl + "helden/held/versionen/" + gruppe, null, "GET", headers);
        try {

            return objectMapper.readValue(is, new TypeReference<List<DSOHeldVersion>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Daten getDaten(Long heldid, int version) {

        InputStream is = RestUtils.request(baseUrl + "helden/held/" + heldid + "/version/" +version, null, "GET", headers);
        try {

            return objectMapper.readValue(is, DSODatenAndEditable.class).getDaten();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getXml(Long heldid) {
        InputStream is = RestUtils.request(baseUrl + "helden/download/" + heldid + "/xml/", null, "GET", headers);
        return is;
    }


}
