package de.failender.dgo.persistance.held;

import de.failender.dgo.persistance.user.UserEntity;

public class HeldWithUser {

    private UserEntity userEntity;
    private HeldEntity heldEntity;

    public HeldEntity getHeldEntity() {
        return heldEntity;
    }

    public void setHeldEntity(HeldEntity heldEntity) {
        this.heldEntity = heldEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
