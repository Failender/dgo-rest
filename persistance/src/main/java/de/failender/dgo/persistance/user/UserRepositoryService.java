package de.failender.dgo.persistance.user;

import java.util.List;

public class UserRepositoryService {

    public static UserEntity findUserByName(String name) {

        return new UserRepository().findUserByName(name);
    }

    public static List<String> findUserPermissions(UserEntity userEntity) {
        return new UserRepository().getUserRights(userEntity.getId());
    }
}
