package de.failender.dgo.persistance.meister.raumplan;

import de.failender.dgo.persistance.BaseMapper;
import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

public class RaumplanMapper extends BaseMapper<RaumplanEntity> {


    public static final RaumplanMapper INSTANCE = new RaumplanMapper();
    public static StringFieldMapper<RaumplanEntity> NAME = new StringFieldMapper<>("NAME", RaumplanEntity::setName, RaumplanEntity::getName);
    public static LongFieldMapper<RaumplanEntity> OWNER = new LongFieldMapper<>("OWNER", RaumplanEntity::setOwner, RaumplanEntity::getOwner);
    public static LocalDateTimeFieldMapper<RaumplanEntity> CRETED_DATE = new LocalDateTimeFieldMapper<>("CREATED_DATE", RaumplanEntity::setCreatedDate, RaumplanEntity::getCreatedDate);

    @Override
    public String table() {
        return "RAUMPLAN";
    }

    @Override
    public RaumplanEntity create() {
        return new RaumplanEntity();
    }

    @Override
    public List<FieldMapper<RaumplanEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, NAME, OWNER, CRETED_DATE);
    }

}
