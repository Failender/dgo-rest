package de.failender.dgo.persistance.asset;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

class AssetRepository extends EzqlRepository<AssetEntity> {

    static final AssetRepository INSTANCE = new AssetRepository();
    @Override
    protected EntityMapper<AssetEntity> getMapper() {
        return AssetMapper.INSTANCE;
    }
}
