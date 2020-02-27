package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;

import java.util.Arrays;
import java.util.List;

public class LagerortMapper extends EntityMapper<LagerortEntity> {

    public static final LongFieldMapper ID = new LongFieldMapper<>("ID", LagerortEntity::setId, LagerortEntity::getId);
    public static final StringFieldMapper NOTIZ = new StringFieldMapper<>("NOTIZ", LagerortEntity::setNotiz, LagerortEntity::getNotiz);
    public static final StringFieldMapper NAME = new StringFieldMapper<>("NAME", LagerortEntity::setName, LagerortEntity::getName);
    public static final LongFieldMapper HELDID= new LongFieldMapper<>("HELDID", LagerortEntity::setHeldid, LagerortEntity::getHeldid);
    public static final LagerortMapper INSTANCE = new LagerortMapper();


    @Override
    public String table() {
        return "LAGERORT";
    }

    @Override
    public LagerortEntity create() {
        return new LagerortEntity();
    }

    private static final List<FieldMapper<LagerortEntity, ?>> FIELDS = Arrays.asList(ID, NOTIZ, NAME, HELDID);
    @Override
    public List<FieldMapper<LagerortEntity, ?>> fieldMappers() {
        return FIELDS;
    }

    @Override
    public LongFieldMapper<LagerortEntity> idField() {
        return ID;
    }
}
