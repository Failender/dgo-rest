package de.failender.dgo.persistance.held.geld;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.IntFieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;

import java.util.Arrays;
import java.util.List;

public class GeldBoerseMapper extends EntityMapper<GeldBoerseEntity> {
    public static final GeldBoerseMapper INSTANCE = new GeldBoerseMapper();

    public static final LongFieldMapper ID = new LongFieldMapper<>("ID", GeldBoerseEntity::setId, GeldBoerseEntity::getId);
    public static final LongFieldMapper HELD_ID = new LongFieldMapper<>("HELD_ID", GeldBoerseEntity::setHeldid, GeldBoerseEntity::getHeldid);
    public static final LongFieldMapper ANZAHL = new LongFieldMapper<>("ANZAHL", GeldBoerseEntity::setAnzahl, GeldBoerseEntity::getAnzahl);
    @Override
    public String table() {
        return "HELD_GELDBOERSE";
    }

    @Override
    public GeldBoerseEntity create() {
        return new GeldBoerseEntity();
    }

    @Override
    public List<FieldMapper<GeldBoerseEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, HELD_ID, ANZAHL);
    }

    @Override
    public LongFieldMapper idField() {
        return ID;
    }
}
