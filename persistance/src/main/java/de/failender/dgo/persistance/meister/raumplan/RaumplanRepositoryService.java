package de.failender.dgo.persistance.meister.raumplan;

import de.failender.dgo.persistance.meister.raumplanebene.RaumplanEbeneRepositoryService;
import de.failender.dgo.security.DgoSecurityContext;

import java.time.LocalDateTime;
import java.util.List;

public class RaumplanRepositoryService {

    public static void persist(RaumplanEntity raumplanEntity) {
        raumplanEntity.setOwner(DgoSecurityContext.getAuthenticatedUser().getId());
        raumplanEntity.setCreatedDate(LocalDateTime.now());
        RaumplanRepository.INSTANCE.persist(raumplanEntity);
    }

    public static List<RaumplanEntity> findRaumplaeneForCurrentUser() {
        Long userId = DgoSecurityContext.getAuthenticatedUser().getId();
        return RaumplanRepository.INSTANCE.findByUserId(userId);
    }

    public static void deleteById(long id) {
        RaumplanEbeneRepositoryService.deleteByParent(id);
        RaumplanRepository.INSTANCE.deleteById(id);
    }
}
