package de.failender.dgo.persistance.held;

import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.EntityNotFoundException;
import de.failender.dgo.security.NoPermissionException;

import java.util.List;

public class HeldRepositoryService {

    public static List<HeldEntity> findByUserId(Long id) {
        return HeldRepository.findByUserIdOrdered(id);
    }

    public static List<HeldEntity> findByGruppeId(Long id, boolean includePrivate, boolean showInactive) {
        if(includePrivate) {
            DgoSecurityContext.checkPermission(DgoSecurityContext.VIEW_ALL);

        }
        return HeldRepository.findByGruppe(id, includePrivate, showInactive);
    }
    public static void saveHeld(HeldEntity heldEntity) {
        new HeldRepository().persist(heldEntity, true);
    }

    /**
     * Finds a held by its id, with only the fields that are needed to check if the user can view this one
     */
    public static HeldEntity findByIdReduced(Long id) {
        HeldEntity heldEntity = new HeldRepository().findByIdReduced(id);
        if(!canCurrentUserViewHeld(heldEntity)) {
            throw new NoPermissionException();
        }
        return heldEntity;
    }
    public static HeldEntity findById(Long id) {
        HeldEntity heldEntity = new HeldRepository().findById(id);


        if(!canCurrentUserViewHeld(heldEntity)) {
            throw new NoPermissionException();
        }

        return heldEntity;
    }

    public static boolean canCurrentUserViewHeld(HeldEntity heldEntity) {
        if (heldEntity == null) {
            throw new EntityNotFoundException("Der Held konnte nicht gefunden werden");
        }
        return DgoSecurityContext.getAuthenticatedUser().getId() == heldEntity.getUserId();
    }



    public static void updatePublic(HeldEntity heldEntity, boolean value) {
        HeldRepository.updatePublic(heldEntity.getId(), value);
    }

    public static void updateActive(HeldEntity heldEntity, boolean value) {
        HeldRepository.updateActive(heldEntity.getId(), value);
    }
}
