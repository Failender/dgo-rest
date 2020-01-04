package de.failender.dgo.persistance.asset;

import de.failender.dgo.persistance.held.uebersicht.HeldUebersichtEntity;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;

import java.util.Arrays;
import java.util.List;

public class AssetMapper extends EntityMapper<AssetEntity> {

    public static final LongFieldMapper<AssetEntity> ID = new LongFieldMapper<>("ID", AssetEntity::setId, AssetEntity::getId);
    public static final LongFieldMapper<AssetEntity> GRUPPE = new LongFieldMapper<>("GRUPPE", AssetEntity::setGruppe, AssetEntity::getGruppe);
    public static final StringFieldMapper<AssetEntity> NAME = new StringFieldMapper<>("NAME", AssetEntity::setName, AssetEntity::getName);

    public static final AssetMapper INSTANCE = new AssetMapper();

    @Override
    public String table() {
        return "ASSET";
    }

    @Override
    public AssetEntity create() {
        return new AssetEntity();
    }

    @Override
    public List<FieldMapper<AssetEntity, ?>> fieldMappers() {
        return Arrays.asList(ID, NAME);
    }

    @Override
    public LongFieldMapper<AssetEntity> idField() {
        return ID;
    }
}
