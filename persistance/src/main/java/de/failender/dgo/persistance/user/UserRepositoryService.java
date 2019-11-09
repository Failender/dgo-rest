package de.failender.dgo.persistance.user;


import java.util.List;

public class UserRepositoryService {

    public static UserEntity findUserByName(String name) {
        return UserRepository.findByName(name);
    }

    public static UserEntity findUserById(Long id) {
        return UserRepository.findById(id);
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

    public static void addRoleForUser(UserEntity userEntity, Long permission) {
        UserRepository.addRoleForUser(userEntity, permission);
    }

    public static void save(UserEntity userEntity) {
        UserRepository.insert(userEntity);
    }
}
