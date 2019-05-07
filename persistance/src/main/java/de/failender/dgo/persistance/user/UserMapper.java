package de.failender.dgo.persistance.user;

import de.failender.ezql.mapper.*;
import de.failender.ezql.queries.SelectQuery;

import java.util.Arrays;
import java.util.List;

public class UserMapper extends EntityMapper<UserEntity> {

    public static final UserMapper INSTANCE = new UserMapper();

    public static final StringFieldMapper<UserEntity> USER_NAME = new StringFieldMapper<>("NAME",
            UserEntity::setName, UserEntity::getName);

    public static final StringFieldMapper<UserEntity> PASSWORD = new StringFieldMapper<UserEntity>("PASSWORD",
            UserEntity::setPassword, UserEntity::getPassword);

    public static final LongFieldMapper<UserEntity> ID = new LongFieldMapper<UserEntity>("ID",
            UserEntity::setId, UserEntity::getId);

    public static final StringFieldMapper<UserEntity> TOKEN = new StringFieldMapper<UserEntity>("TOKEN",
            UserEntity::setToken, UserEntity::getToken);

    public static final LongFieldMapper<UserEntity> GRUPPE = new LongFieldMapper<UserEntity>("GRUPPE_ID",
            UserEntity::setGruppe, UserEntity::getGruppe);

    public static final BooleanFieldMapper<UserEntity> CAN_WRITE = new BooleanFieldMapper<UserEntity>("CAN_WRITE",
            UserEntity::setCanWrite, UserEntity::isCanWrite);




    @Override
    public String table() {
        return "USERS";
    }

    @Override
    public UserEntity create() {
        return new UserEntity();
    }


    @Override
    public List<FieldMapper<UserEntity, ?>> fieldMappers() {
        return Arrays.asList(USER_NAME,PASSWORD, ID, TOKEN, GRUPPE, CAN_WRITE);
    }

    @Override
    public LongFieldMapper<UserEntity> idField() {
        return ID;
    }
}
