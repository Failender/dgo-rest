package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;

import java.util.Arrays;
import java.util.List;

public class GegenstandtoLagerortMapper extends EntityMapper<GegenstandToLagerortEntity> {

    public static final GegenstandtoLagerortMapper INSTANCE = new GegenstandtoLagerortMapper();
    public static final LongFieldMapper ID = new LongFieldMapper<>("ID", GegenstandToLagerortEntity::setId, GegenstandToLagerortEntity::getId);
    public static final LongFieldMapper LAGERORT = new LongFieldMapper<>("LAGERORT", GegenstandToLagerortEntity::setLagerort, GegenstandToLagerortEntity::getLagerort);
    public static final StringFieldMapper NAME = new StringFieldMapper<>("NAME", GegenstandToLagerortEntity::setName, GegenstandToLagerortEntity::getName);
    public static final LongFieldMapper AMOUNT = new LongFieldMapper<>("AMOUNT", GegenstandToLagerortEntity::setAmount, GegenstandToLagerortEntity::getAmount);


    @Override
    public String table() {
        return "GEGENSTAND_TO_LAGERORT";
    }

    @Override
    public GegenstandToLagerortEntity create() {
        return new GegenstandToLagerortEntity();
    }

    private static final List<FieldMapper<GegenstandToLagerortEntity, ?>> FIELDS = Arrays.asList(ID, LAGERORT, NAME, AMOUNT);
    @Override
    public List<FieldMapper<GegenstandToLagerortEntity, ?>> fieldMappers() {
        return FIELDS;
    }

    @Override
    public LongFieldMapper<GegenstandToLagerortEntity> idField() {
        return ID;
    }
}
