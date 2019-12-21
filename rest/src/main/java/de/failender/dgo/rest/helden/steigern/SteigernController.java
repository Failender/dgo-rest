package de.failender.dgo.rest.helden.steigern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.*;
import de.failender.heldensoftware.xml.datenxml.Ap;
import de.failender.heldensoftware.xml.listtalente.ListTalente;
import de.failender.heldensoftware.xml.listtalente.SteigerungsTalent;
import io.javalin.Context;
import io.javalin.Javalin;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SteigernController {


    private static final String PREFIX = "/api/helden/steigern/";
    private static final String FOR_HELD = PREFIX + "held/:held/";
    private static final String EREIGNIS = PREFIX + "held/:held/ereignis";
    private static final String AP_FOR_HELD = FOR_HELD + "ap/";
    public SteigernController(Javalin app) {
        app.get(FOR_HELD, this::getSteigerungenForHeld);
        app.get(AP_FOR_HELD, this::getApForHeldUncached);
        app.put(FOR_HELD, this::updateSteigerungenForHeld);
        app.post(FOR_HELD, this::steigern);
        app.post(EREIGNIS, this::addEreignis);
    }

    private void steigern(Context context) throws IOException {
        Long id = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(id);
        UserEntity userEntity = UserRepositoryService.findUserById(heldEntity.getUserId());
        lockHeld(heldEntity);
        List<SteigernDto> steigerungen = new ObjectMapper().readValue(context.bodyAsBytes(), new TypeReference<List<SteigernDto>>(){});
        for (SteigernDto steigernDto : steigerungen) {
            steigern(heldEntity, userEntity, steigernDto);
        }
        getSteigerungenForHeld(context);

    }

    private void steigern(HeldEntity heldEntity, UserEntity userEntity, SteigernDto steigernDto) {
        Beans.HELDEN_API.request(new RaiseTalentRequest(new TokenAuthentication(userEntity.getToken()), heldEntity.getId(), steigernDto.getTalent(), steigernDto.getTalentwert()))
                .block();
    }

    private void lockHeld(HeldEntity heldEntity){
        HeldRepositoryService.updateLockExpire(heldEntity, LocalDateTime.now().plusMinutes(30));

    }

    private void getSteigerungenForHeld(Context context) {
        Long id = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(id);
        UserEntity userEntity = UserRepositoryService.findUserById(heldEntity.getUserId());
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldEntity);

        ListTalente talente = Beans.HELDEN_API.request(new ListTalenteRequest(new TokenAuthentication(userEntity.getToken()), heldEntity.getId()))
                .block();
        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(id, new TokenAuthentication(userEntity.getToken()), versionEntity.getCacheId()), false)
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        Element talentliste = (Element) held.getElementsByTagName("talentliste").item(0);

        List<SteigerungsTalentDto> dtos = talente.getTalent()
                .stream()
                .map(entry -> {
                    return new SteigerungsTalentDto(entry);
                }).collect(Collectors.toList());



        context.json(dtos);
    }

    private boolean getIsSe(String talent, Element talentliste) {
        for(int i = 0; i<talentliste.getChildNodes().getLength(); i++) {
            Node node = talentliste.getChildNodes().item(i);
            if(!(node instanceof Element)) {
                continue;
            }
            Element e = (Element) node;
            if(e.getAttribute("name").equals(talent)) {
                String value = e.getAttribute("se");
                return value != null && value.equals("true");
            }
        }
        return false;
    }


    private void updateSteigerungenForHeld(Context context) throws IOException {
        Long id = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(id);
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldEntity);
        UserEntity userEntity = UserRepositoryService.findUserById(heldEntity.getUserId());
        List<SteigerungsTalent> talente = new ObjectMapper().readValue(context.bodyAsBytes(), new TypeReference<List<SteigerungsTalent>>(){});

        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(id, new TokenAuthentication(userEntity.getToken()), versionEntity.getCacheId()))
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        Element talentliste = (Element) held.getElementsByTagName("talentliste").item(0);
        for (SteigerungsTalent steigerungsTalent : talente) {
            updateLernmethode(steigerungsTalent, talentliste);
        }
        xml = XmlUtil.toString(held.getOwnerDocument());
        Beans.HELDEN_API.request(new UpdateXmlRequest(new TokenAuthentication(userEntity.getToken()), xml))
                .block();
        lockHeld(heldEntity);


        this.getSteigerungenForHeld(context);

    }

    private void updateLernmethode(SteigerungsTalent steigerungsTalent, Element talentliste){

        for(int i = 0; i<talentliste.getChildNodes().getLength(); i++) {
            Node node = talentliste.getChildNodes().item(i);
            if(!(node instanceof Element)) {
                continue;
            }
            Element e = (Element) node;
            if(e.getAttribute("name").equals(steigerungsTalent.getTalent())) {
                e.setAttribute("lernmethode", steigerungsTalent.getLernmethode());

                return;
            }
        }
    }

    private void getApForHeldUncached(Context context) {
        Long id = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(id);
        UserEntity userEntity = UserRepositoryService.findUserById(heldEntity.getUserId());
        Ap ap = Beans.HELDEN_API.request(new ReturnHeldDatenRequest(heldEntity.getId(), new TokenAuthentication(userEntity.getToken()), null, true))
                .block().getAngaben().getAp();
        context.json(ap);
    }

    private void addEreignis(Context context) {
        Long id = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(id);
        UserEntity userEntity = UserRepositoryService.findUserById(heldEntity.getUserId());
        AddEreignisDto dto = context.bodyAsClass(AddEreignisDto.class);
        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(id, new TokenAuthentication(userEntity.getToken()), null, true))
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        Element basis = (Element) held.getElementsByTagName("basis").item(0);
        Element abenteuerpunkte = (Element) basis.getElementsByTagName("abenteuerpunkte").item(0);
        int abenteuerpunkteInt = Integer.parseInt(abenteuerpunkte.getAttribute("value"));
        abenteuerpunkteInt += dto.getAp();
        abenteuerpunkte.setAttribute("value", String.valueOf(abenteuerpunkteInt));

        Element freieabenteuerpunkte = (Element) basis.getElementsByTagName("freieabenteuerpunkte").item(0);
        int freieabenteuerpunkteInt = Integer.parseInt(freieabenteuerpunkte.getAttribute("value"));
        freieabenteuerpunkteInt += dto.getAp();
        freieabenteuerpunkte.setAttribute("value", String.valueOf(freieabenteuerpunkteInt));

        Element ereignisse = (Element) held.getElementsByTagName("ereignisse").item(0);
        Element ereignis = ereignisse.getOwnerDocument().createElement("ereignis");
        ereignis.setAttribute("Abenteuerpunkte", String.valueOf(dto.getAp()));
        ereignis.setAttribute("kommentar", dto.getName() + "Gesamt AP: " + dto.getAp()+ " Verfuegbare AP: " + dto.getAp());
        ereignis.setAttribute("obj", "Abenteuerpunkte (Hinzugewinn)");
        ereignis.setAttribute("text", "Ereignis eingeben");
        ereignis.setAttribute("time", String.valueOf(System.currentTimeMillis()));
        ereignis.setAttribute("version", "HS 5.5.4");
        ereignisse.appendChild(ereignis);

        xml = XmlUtil.toString(ereignisse.getOwnerDocument());
        Beans.HELDEN_API.request(new UpdateXmlRequest(new TokenAuthentication(userEntity.getToken()), xml))
                .block();
        this.getApForHeldUncached(context);
    }
}
