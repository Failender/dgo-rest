package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionMapper;
import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

public class ZauberspeicherMapper extends EntityMapper<ZauberspeicherEntity> {

    public static final ZauberspeicherMapper INSTANCE = new ZauberspeicherMapper();

    public static final LongFieldMapper<ZauberspeicherEntity> ID = new LongFieldMapper<>("ID", ZauberspeicherEntity::setId, ZauberspeicherEntity::getId);
    public static final LongFieldMapper<ZauberspeicherEntity> HELD_ID = new LongFieldMapper<>("HELDID", ZauberspeicherEntity::setHeldid, ZauberspeicherEntity::getHeldid);
    public static final IntFieldMapper<ZauberspeicherEntity> KOSTEN = new IntFieldMapper<>("KOSTEN", ZauberspeicherEntity::setKosten, ZauberspeicherEntity::getKosten);
    public static final IntFieldMapper<ZauberspeicherEntity> QUALITAET = new IntFieldMapper<>("QUALITAET", ZauberspeicherEntity::setQualitaet, ZauberspeicherEntity::getQualitaet);
    public static final StringFieldMapper<ZauberspeicherEntity> KOMPLEXITAET = new StringFieldMapper<>("KOMPLEXITAET", ZauberspeicherEntity::setKomplexitaet, ZauberspeicherEntity::getKomplexitaet);
    public static final StringFieldMapper<ZauberspeicherEntity> SPOMOS = new StringFieldMapper<>("SPOMOS", ZauberspeicherEntity::setSpomos, ZauberspeicherEntity::getSpomos);
    public static final StringFieldMapper<ZauberspeicherEntity> ZAUBER = new StringFieldMapper<>("ZAUBER", ZauberspeicherEntity::setZauber, ZauberspeicherEntity::getZauber);


    @Override
    public String table() {
        return "ZAUBERSPEICHER";
    }

    @Override
    public ZauberspeicherEntity create() {
        return new ZauberspeicherEntity();
    }

    @Override
    public List<FieldMapper<ZauberspeicherEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, HELD_ID, KOSTEN, KOMPLEXITAET, SPOMOS, QUALITAET, ZAUBER);
    }

    @Override
    public LongFieldMapper idField() {
        return ID;
    }
}
