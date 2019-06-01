package de.failender.dgo.persistance.held.uebersicht;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.IntFieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;

import java.util.Arrays;
import java.util.List;

public class HeldUebersichtMapper extends EntityMapper<HeldUebersichtEntity> {

    public static final HeldUebersichtMapper INSTANCE = new HeldUebersichtMapper();

    public static final LongFieldMapper<HeldUebersichtEntity> ID = new LongFieldMapper<>("ID", HeldUebersichtEntity::setId, HeldUebersichtEntity::getId);
    public static final LongFieldMapper<HeldUebersichtEntity> HELDID = new LongFieldMapper<>("HELDID", HeldUebersichtEntity::setHeldid, HeldUebersichtEntity::getHeldid);
    public static final IntFieldMapper<HeldUebersichtEntity> LEP = new IntFieldMapper<>("LEP", HeldUebersichtEntity::setLep, HeldUebersichtEntity::getLep);
    public static final IntFieldMapper<HeldUebersichtEntity> ASP = new IntFieldMapper<>("ASP", HeldUebersichtEntity::setAsp, HeldUebersichtEntity::getAsp);

    @Override
    public String table() {
        return "HELD_UEBERSICHT";
    }

    @Override
    public HeldUebersichtEntity create() {
        return new HeldUebersichtEntity();
    }

    @Override
    public List<FieldMapper<HeldUebersichtEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, HELDID, LEP, ASP);
    }

    @Override
    public LongFieldMapper<HeldUebersichtEntity> idField() {
        return ID;
    }


}

