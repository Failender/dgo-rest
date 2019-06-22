package de.failender.ezql.user;

import de.failender.ezql.mapper.*;
import de.failender.ezql.mapper.array.IntArrayFieldMapper;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;

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

	public static final UUIDFieldMapper<UserEntity> UUID = new UUIDFieldMapper<>("UUID", UserEntity::setUuid, UserEntity::getUuid);

	public static final BooleanFieldMapper<UserEntity> ACTIVE = new BooleanFieldMapper<UserEntity>("ACTIVE",
			UserEntity::setActive, UserEntity::isActive);

	public static final IntArrayFieldMapper<UserEntity> NUMBERS = new IntArrayFieldMapper<>("NUMBERS", UserEntity::setNumbers, UserEntity::getNumbers);





	public static List<UserEntity> selectAll() {
		return SelectQuery.Builder.selectAll(INSTANCE).build().execute();
	}

	public static List<UserEntity> selectAllOnlyName() {

		return SelectQuery.Builder.select(INSTANCE, USER_NAME).build().execute();
	}

	public static UserEntity selectOnlyName(Long id) {
		return SelectQuery.Builder.select(INSTANCE, USER_NAME).limit(1).where(ID, id).build().execute().get(0);
	}

	public static UserEntity selectById(Long id) {
		return SelectQuery.Builder.selectAll(INSTANCE).where(ID, id).limit(1).build().execute().get(0);
	}

	public static void updateName(Long id, String name) {
		UpdateQuery.Builder.update(INSTANCE).where(ID, id).update(USER_NAME, name).build().execute();
	}


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
		return Arrays.asList(USER_NAME, PASSWORD, ID, ACTIVE, UUID, NUMBERS);
	}

	@Override
	public LongFieldMapper idField() {
		return ID;
	}
}
