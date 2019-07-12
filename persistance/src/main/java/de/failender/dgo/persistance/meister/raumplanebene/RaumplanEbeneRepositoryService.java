package de.failender.dgo.persistance.meister.raumplanebene;

import java.util.List;

public class RaumplanEbeneRepositoryService {

    public static void persist(RaumplanEbeneEntity raumplanEbeneEntity){
        RaumplanEbeneRepository.INSTANCE.persist(raumplanEbeneEntity);
    }

    public static void deleteById(Long id) {
        RaumplanEbeneRepository.INSTANCE.deleteById(id);
    }

    public static List<RaumplanEbeneEntity> findByParent(long parent) {
        return RaumplanEbeneRepository.INSTANCE.findByParent(parent);
    }

    public static void deleteByParent(long id) {
        RaumplanEbeneRepository.INSTANCE.deleteByParent(id);
    }
}
