package de.failender.dgo.persistance.held;

public class VersionRepositoryService {
    public static VersionEntity findLatestVersion(HeldEntity heldEntity) {
        return new VersionRepository().findFirstByHeldidOrderByVersionDesc(heldEntity.getId());
    }

    public static void saveVersion(HeldEntity heldEntity, VersionEntity versionEntity) {
        new VersionRepository().save(versionEntity);
    }
}
