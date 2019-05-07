package de.failender.dgo.persistance.user;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.IntFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;
import de.failender.ezql.queries.SelectQuery;

import java.util.Arrays;
import java.util.List;

public class UserMapper extends EntityMapper<UserEntity> {

    public static final UserMapper INSTANCE = new UserMapper();

    public static final StringFieldMapper<UserEntity> USER_NAME = new StringFieldMapper<>("NAME",
            UserEntity::setName, UserEntity::getName);

    public static final StringFieldMapper<UserEntity> PASSWORD = new StringFieldMapper<UserEntity>("PASSWORD",
            UserEntity::setPassword, UserEntity::getPassword);

    public static final IntFieldMapper<UserEntity> ID = new IntFieldMapper<UserEntity>("ID",
            UserEntity::setId, UserEntity::getId);

    public static final StringFieldMapper<UserEntity> TOKEN = new StringFieldMapper<UserEntity>("TOKEN",
            UserEntity::setToken, UserEntity::getToken);

    public static final IntFieldMapper<UserEntity> GRUPPE = new IntFieldMapper<UserEntity>("GRUPPE_ID",
            UserEntity::setGruppe, UserEntity::getGruppe);


    @Override
    public String table() {
        return "USERS";
    }

    @Override
    public UserEntity create() {
        return new UserEntity();
    }

    @Override
    public Class<UserEntity> entityClass() {
        return UserEntity.class;
    }

    @Override
    public List<FieldMapper<UserEntity, ?>> fieldMappers() {
        return Arrays.asList(USER_NAME,PASSWORD, ID, TOKEN, GRUPPE);
    }

    @Override
    public IntFieldMapper<UserEntity> idField() {
        return ID;
    }
}
