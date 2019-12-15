package de.failender.dgo.persistance.held;

import java.beans.Beans;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VersionRepositoryService {
    public static VersionEntity findLatestVersion(HeldEntity heldEntity) {
        return VersionRepository.INSTANCE.findFirstByHeldidOrderByVersionDesc(heldEntity.getId());
    }



    public static VersionEntity findVersion(HeldEntity heldEntity, int version) {
        return VersionRepository.INSTANCE.findByHeldAndVersion(heldEntity.getId(), version);
    }

    public static void saveVersion(HeldEntity heldEntity, VersionEntity versionEntity) {
        VersionRepository.INSTANCE.persist(versionEntity);
    }

    public static Optional<VersionEntity> findByHeldidAndCreated(HeldEntity held, LocalDateTime stand) {
        return VersionRepository.INSTANCE.findByHeldIdAndCreated(held.getId(), stand);
    }

    public static List<VersionEntity> findVersionsByHeld(HeldEntity heldEntity) {
        return VersionRepository.INSTANCE.findVersionsByHeldOrderByVersionAsc(heldEntity.getId());
    }

    public static void updateVersion(VersionEntity versionEntity, int version) {
        VersionRepository.INSTANCE.updateVersion(versionEntity.getId(), version);
    }

    public static void deleteVersions(List<VersionEntity> versionEntities) {
        VersionRepository.INSTANCE.deleteVersions(versionEntities.stream().map(VersionEntity::getId).collect(Collectors.toList()));
    }
}
