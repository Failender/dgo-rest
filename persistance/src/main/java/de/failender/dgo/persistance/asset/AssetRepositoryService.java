package de.failender.dgo.persistance.asset;

import java.util.List;

public class AssetRepositoryService {

    public static void save(AssetEntity assetEntity) {
        AssetRepository.INSTANCE.persist(assetEntity);
    }

    public static List<AssetEntity> findAll() {
        return AssetRepository.INSTANCE.findAll();
    }
}
