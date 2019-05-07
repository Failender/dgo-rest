package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.xml.datenxml.Daten;
import de.failender.heldensoftware.xml.datenxml.Ereignis;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VersionService {
    public static VersionEntity persistVersion(HeldEntity held, UserEntity user, int version, String xml, UUID uuid, Daten daten) {
        Date date = XmlUtil.getStandFromString(xml);
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setVersion(version);
        versionEntity.setHeldid(held.getId());
        versionEntity.setCacheId(uuid);
        versionEntity.setCreatedDate(date);
        versionEntity.setLastEvent(extractLastEreignisString(daten.getEreignisse().getEreignis()));
        versionEntity.setAp(daten.getAngaben().getAp().getGesamt().intValue());
        VersionRepositoryService.saveVersion(held, versionEntity);


        return versionEntity;
    }

    public static String extractLastEreignisString(List<Ereignis> ereignisse) {
        Ereignis ereignis = extractLastEreignis(ereignisse);
        if (ereignis == null) {
            return null;
        }
        String s = ereignis.getKommentar();
        int index = s.indexOf("Gesamt AP");
        if (index == -1) {
            return s;
        }
        s = s.substring(0, index);
        return s;
    }

    public static Ereignis extractLastEreignis(List<Ereignis> ereignisse) {
        for (int i = ereignisse.size() - 1; i >= 0; i--) {
            if (ereignisse.get(i).getAp() > 0) {
                return ereignisse.get(i);
            }
        }
        return null;
    }

}
