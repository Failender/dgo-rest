package de.failender.dgo.persistance.meister.raumplanebene;

import de.failender.dgo.persistance.BaseMapper;
import de.failender.dgo.persistance.meister.raumplan.RaumplanEntity;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;

import java.util.Arrays;
import java.util.List;

public class RaumplanEbeneMapper extends BaseMapper<RaumplanEbeneEntity> {

    public static final RaumplanEbeneMapper INSTANCE = new RaumplanEbeneMapper();
    public static StringFieldMapper<RaumplanEbeneEntity> NAME = new StringFieldMapper<>("NAME", RaumplanEbeneEntity::setName, RaumplanEbeneEntity::getName);
    public static StringFieldMapper<RaumplanEbeneEntity> BESCHREIBUNG = new StringFieldMapper<>("BESCHREIBUNG", RaumplanEbeneEntity::setBeschreibung, RaumplanEbeneEntity::getBeschreibung);
    public static LongFieldMapper<RaumplanEbeneEntity> PARENT = new LongFieldMapper<>("PARENT", RaumplanEbeneEntity::setParent, RaumplanEbeneEntity::getParent);


    @Override
    public String table() {
        return "RAUMPLAN_EBENE";
    }

    @Override
    public RaumplanEbeneEntity create() {
        return new RaumplanEbeneEntity();
    }

    @Override
    public List<FieldMapper<RaumplanEbeneEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, NAME, BESCHREIBUNG, PARENT);
    }

}
