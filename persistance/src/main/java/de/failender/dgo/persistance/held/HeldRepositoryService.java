package de.failender.dgo.persistance.held;

import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.NoPermissionException;

import java.util.List;

public class HeldRepositoryService {
    public static List<HeldEntity> findByUserId(Long id) {
        return HeldRepository.findByUserIdOrdered(id);
    }

    public static void saveHeld(HeldEntity heldEntity) {
        HeldRepository.persist(heldEntity);
    }

    public static HeldEntity findById(Long id) {
        HeldEntity heldEntity = HeldRepository.findById(id);


        if(!canCurrentUserViewHeld(heldEntity)) {
            throw new NoPermissionException();
        }

        return heldEntity;
    }

    public static boolean canCurrentUserViewHeld(HeldEntity heldEntity) {
        return DgoSecurityContext.getAuthenticatedUser().getId() == heldEntity.getUserId();
    }



    public static void updatePublic(HeldEntity heldEntity, boolean value) {
        HeldRepository.updatePublic(heldEntity.getId(), value);
    }

    public static void updateActive(HeldEntity heldEntity, boolean value) {
        HeldRepository.updateActive(heldEntity.getId(), value);
    }
}
