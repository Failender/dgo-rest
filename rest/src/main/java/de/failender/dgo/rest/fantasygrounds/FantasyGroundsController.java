package de.failender.dgo.rest.fantasygrounds;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import de.failender.dgo.integration.Beans;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.EntityNotFoundException;
import de.failender.fantasygrounds.CampaignInformation;
import de.failender.fantasygrounds.Character;
import de.failender.fantasygrounds.FantasyGroundsConverterService;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import io.javalin.Context;
import io.javalin.Javalin;
import io.javalin.UploadedFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FantasyGroundsController {

    private static final String PREFIX = "api/fantasygrounds/";
    private static final String DB = PREFIX + "db";
    private static final String EXPORT = PREFIX + "export";


    private static final Cache<String, CampaignInformation> campaignCache = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    public FantasyGroundsController(Javalin app) {
        app.post(DB, this::uploadDbXml);
        app.get(DB, this::getUploadedXml);
        app.post(EXPORT, this::export);
    }

    private void uploadDbXml(Context context) {
        UploadedFile file = context.uploadedFile("body");
        if (file == null) {
            return;
        }
        InputStream is = file.getContent();
        CampaignInformation campaignInformation = FantasyGroundsConverterService.parseCampaignInformation(is);
        campaignCache.put(DgoSecurityContext.getAuthenticatedUser().getName(), campaignInformation);
        context.json(campaignInformation);
    }

    private void getUploadedXml(Context context) {
        CampaignInformation campaignInformation = campaignCache.getIfPresent(DgoSecurityContext.getAuthenticatedUser().getName());
        if (campaignInformation != null) {
            context.json(campaignInformation);
        }
    }

    private void export(Context context) {
        CampaignInformation campaignInformation = campaignCache.getIfPresent(DgoSecurityContext.getAuthenticatedUser().getName());
        if (campaignInformation == null) {
            throw new EntityNotFoundException("Es ist derzeit keine db xml hochgeladen");
        }
        ExportSettingsDTO exportSettingsDTO = context.bodyAsClass(ExportSettingsDTO.class);
        List<Daten> heldenDaten = new ArrayList<>();
        List<Character> exportCharacters = new ArrayList<>();
        for (ExportCharacterDTO character : exportSettingsDTO.getCharacters()) {
            VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(HeldRepositoryService.findById(character.getId()));
            Daten daten = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(character.getId(), null, versionEntity.getCacheId())).block();
            heldenDaten.add(daten);
            if (character.getCampaignId() != null) {
                campaignInformation.getCharacters().stream().filter(entry -> entry.getId() == character.getCampaignId()).findFirst().ifPresent(campaignCharacter -> {
                    exportCharacters.add(campaignCharacter);
                    campaignCharacter.setName(daten.getAngaben().getName());
                });
            }
        }
        String xml = FantasyGroundsConverterService.convert(heldenDaten, campaignInformation.getXml(), exportCharacters);

        try {
            File file = File.createTempFile("temp", null);
            file.deleteOnExit();

            try (OutputStreamWriter writer =
                         new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.ISO_8859_1)) {
                writer.write(xml);
                context.result(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        MigrationResult migrationResult = new MigrationResult();
//        migrationResult.setXml(xml);
//
//        context.json(migrationResult);
        campaignCache.invalidate(DgoSecurityContext.getAuthenticatedUser().getName());
    }
}
