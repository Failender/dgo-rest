package de.failender.dgo.persistance.held;

public class VersionRepositoryService {
    public static VersionEntity findLatestVersion(HeldEntity heldEntity) {
        return VersionRepository.findFirstByHeldidOrderByVersionDesc(heldEntity.getId());
    }

    public static VersionEntity findVersion(HeldEntity heldEntity, int version) {
        return VersionRepository.findByHeldAndVersion(heldEntity.getId(), version);
    }

    public static void saveVersion(HeldEntity heldEntity, VersionEntity versionEntity) {
        new VersionRepository().persist(versionEntity);
    }
}
