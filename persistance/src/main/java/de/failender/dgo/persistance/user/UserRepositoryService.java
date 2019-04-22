package de.failender.dgo.persistance.user;

import javax.persistence.NoResultException;
import java.util.List;

public class UserRepositoryService {

    public static UserEntity findUserByName(String name) {

        return new UserRepository().findBy(UserEntity.FIELD_NAME, name);
    }

    public static List<String> findUserPermissions(UserEntity userEntity) {
        return new UserRepository().getUserRights(userEntity.getId());
    }

    public static List<UserEntity> findAll() {
        return new UserRepository().findAll();
    }

    public static boolean existsByName(String name) {
        return new UserRepository().existsBy(UserEntity.FIELD_NAME, name);
    }

    public static void save(UserEntity userEntity) {
        new UserRepository().save(userEntity);
    }
}
