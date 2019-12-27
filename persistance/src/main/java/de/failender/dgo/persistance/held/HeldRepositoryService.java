package de.failender.dgo.persistance.held;

import de.failender.dgo.security.DgoSecurityContext;
import de.failender.dgo.security.EntityNotFoundException;
import de.failender.dgo.security.NoPermissionException;

import java.time.LocalDateTime;
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
        if (heldEntity.isPublic()) {
            return true;
        }
        if (!DgoSecurityContext.isAuthenticated()) {
            return false;
        }
        if (DgoSecurityContext.getAuthenticatedUser().getId() == heldEntity.getUserId()) {
            return true;
        }
        DgoSecurityContext.checkPermission(DgoSecurityContext.VIEW_ALL);
        return true;
    }

    public static boolean canCurrentUserEditHeld(HeldEntity heldEntity) {
        if (heldEntity == null) {
            throw new EntityNotFoundException("Der Held konnte nicht gefunden werden");
        }
        if (!DgoSecurityContext.isAuthenticated()) {
            return false;
        }
        if (DgoSecurityContext.getAuthenticatedUser().getId() == heldEntity.getUserId()) {
            return true;
        }
        DgoSecurityContext.checkPermission(DgoSecurityContext.EDIT_ALL);
        return true;

    }

    public static boolean canCurrentUserEditHeldBool(HeldEntity heldEntity) {
        try {
            return canCurrentUserEditHeld(heldEntity);
        } catch (NoPermissionException e) {
            return false;
        }
    }

    public static List<HeldEntity> findAllReduced() {
        return HeldRepository.INSTANCE.findAllReduced();
    }

    public static List<HeldEntity> findAll() {
        return HeldRepository.INSTANCE.findAll();
    }

    public static void updatePublic(HeldEntity heldEntity, boolean value) {
        canCurrentUserEditHeld(heldEntity);
        HeldRepository.updatePublic(heldEntity.getId(), value);
    }

    public static void updateActive(HeldEntity heldEntity, boolean value) {
        canCurrentUserEditHeld(heldEntity);
        HeldRepository.updateActive(heldEntity.getId(), value);
    }

    public static void updateLockExpire(HeldEntity heldEntity, LocalDateTime value) {
        canCurrentUserEditHeld(heldEntity);
        HeldRepository.updateLockExpire(heldEntity.getId(), value);
    }

    public static boolean existsById(Long id) {
        return HeldRepository.INSTANCE.existsById(id);
    }
}
