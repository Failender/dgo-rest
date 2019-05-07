package de.failender.dgo.persistance.user;

import de.failender.ezql.queries.InsertQuery;

import javax.persistence.NoResultException;
import java.util.List;

public class UserRepositoryService {

    public static UserEntity findUserByName(String name) {
        return UserRepository.findByName(name);
    }

    public static List<String> findUserPermissions(UserEntity userEntity) {
        return UserRepository.getPermissionsForUser(userEntity.getId());
    }

    public static List<UserEntity> findAll() {
        return UserRepository.selectAll();
    }

    public static boolean existsByName(String name) {
        //TODO create a real exist by query
        return UserRepository.findByName(name) != null;
    }

    public static void save(UserEntity userEntity) {
        UserRepository.insert(userEntity);
    }
}
