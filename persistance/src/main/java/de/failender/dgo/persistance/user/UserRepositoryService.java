package de.failender.dgo.persistance.user;

public class UserRepositoryService {

    public UserEntity findUserByName(String name) {

        return new UserRepository().findUserByName(name);
    }
}
