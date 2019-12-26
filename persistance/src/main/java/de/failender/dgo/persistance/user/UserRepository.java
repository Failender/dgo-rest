package de.failender.dgo.persistance.user;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static de.failender.ezql.mapper.EntityMapper.firstOrNull;

class UserRepository {


	public static List<UserEntity> selectAll() {
		return SelectQuery.Builder.selectAll(UserMapper.INSTANCE).build().execute();
	}

	public static UserEntity findById(Long id) {
		return firstOrNull(SelectQuery.Builder.selectAll(UserMapper.INSTANCE).where(UserMapper.ID, id).limit(1).build().execute());
	}

	public static void addRoleForUser(UserEntity userEntity, Long permission) {
		String permissionInsert = "INSERT INTO ROLES_TO_USER VALUES (" + permission + ", " + userEntity.getId() + ")";
		EzqlConnector.execute(permissionInsert);
	}

	public static UserEntity findByName(String name) {
		return firstOrNull(SelectQuery.Builder.selectAll(UserMapper.INSTANCE).where(UserMapper.USER_NAME, name).limit(1).build().execute());
	}

	public static List<String> getPermissionsForUser(Long id) {
		try {

            ResultSet rs = EzqlConnector.createStatement().executeQuery("SELECT RIGHTS.NAME FROM USERS U INNER JOIN ROLES_TO_USER RTU ON RTU.USER_ID = U.ID INNER JOIN ROLES_TO_RIGHTS RTR ON RTR.ROLE_ID = RTU.ROLE_ID INNER JOIN RIGHTS ON RIGHTS.ID = RTR.RIGHT_ID WHERE U.ID = " + id);
			List<String> permissions = new ArrayList<>();
			while(rs.next()) {
				permissions.add(rs.getString("name"));
			}
			return permissions;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void insert(UserEntity userEntity) {
		new InsertQuery<>(UserMapper.INSTANCE, userEntity).execute();
	}
}
