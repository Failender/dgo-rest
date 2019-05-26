package de.failender.dgo.persistance.held.inventar;

import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

/*
private String name;
    private Long heldid;
    private Integer container;
    private Integer gewicht;

 */
public class HeldInventarMapper extends EntityMapper<HeldInventarEntity> {

    public static final HeldInventarMapper INSTANCE = new HeldInventarMapper();
    public static final LongFieldMapper<HeldInventarEntity> ID = new LongFieldMapper<>("ID", HeldInventarEntity::setId, HeldInventarEntity::getId);
    public static final StringFieldMapper<HeldInventarEntity> NAME = new StringFieldMapper<>("NAME", HeldInventarEntity::setName, HeldInventarEntity::getName);
    public static final LongFieldMapper<HeldInventarEntity> HELDID = new LongFieldMapper<>("HELDID", HeldInventarEntity::setHeldid, HeldInventarEntity::getHeldid);
    public static final IntFieldMapper<HeldInventarEntity> CONTAINER = new IntFieldMapper<>("CONTAINER", HeldInventarEntity::setContainer, HeldInventarEntity::getContainer);
    public static final FloatFieldMapper<HeldInventarEntity> GEWICHT = new FloatFieldMapper<>("GEWICHT", HeldInventarEntity::setGewicht, HeldInventarEntity::getGewicht);
    public static final StringFieldMapper<HeldInventarEntity> NOTIZ = new StringFieldMapper<>("NOTIZ", HeldInventarEntity::setNotiz, HeldInventarEntity::getNotiz);
    public static final IntFieldMapper<HeldInventarEntity> ANZAHL = new IntFieldMapper<>("ANZAHL", HeldInventarEntity::setAnzahl, HeldInventarEntity::getAnzahl);

    @Override
    public String table() {
        return "HELD_INVENTAR";
    }

    @Override
    public HeldInventarEntity create() {
        return new HeldInventarEntity();
    }

    @Override
    public List<FieldMapper<HeldInventarEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, NAME, HELDID, CONTAINER, GEWICHT, NOTIZ, ANZAHL);
    }

    @Override
    public LongFieldMapper idField() {
        return ID;
    }
}
