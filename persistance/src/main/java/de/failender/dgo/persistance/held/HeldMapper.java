package de.failender.dgo.persistance.held;


import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

public class HeldMapper extends EntityMapper<HeldEntity> {



	public static final LongFieldMapper<HeldEntity> ID = new LongFieldMapper<>("ID", HeldEntity::setId, HeldEntity::getId);
	public static final LongFieldMapper<HeldEntity> USER_ID = new LongFieldMapper<>("USER_ID", HeldEntity::setUserId, HeldEntity::getUserId);
	public static final StringFieldMapper<HeldEntity> NAME = new StringFieldMapper<>("NAME", HeldEntity::setName, HeldEntity::getName);
	public static final BooleanFieldMapper<HeldEntity> ACTIVE = new BooleanFieldMapper<>("ACTIVE", HeldEntity::setActive, HeldEntity::isActive);
	public static final BooleanFieldMapper<HeldEntity> DELETED = new BooleanFieldMapper<>("DELETED", HeldEntity::setDeleted, HeldEntity::isDeleted);
	public static final BooleanFieldMapper<HeldEntity> PUBLIC = new BooleanFieldMapper<>("PUBLIC", HeldEntity::setPublic, HeldEntity::isPublic);
	public static final LongFieldMapper<HeldEntity> GRUPPE = new LongFieldMapper<>("GRUPPE_ID", HeldEntity::setGruppe, HeldEntity::getGruppe);
	public static final LongFieldMapper<HeldEntity> HKEY = new LongFieldMapper<>("HKEY", HeldEntity::setKey, HeldEntity::getKey);
	public static final LocalDateTimeFieldMapper<HeldEntity> LOCK_EXPIRE = new LocalDateTimeFieldMapper<>("LOCK_EXPIRE", HeldEntity::setLockExpire, HeldEntity::getLockExpire);

	public static final HeldMapper INSTANCE = new HeldMapper();



	@Override
	public String table() {
		return "HELDEN";
	}

	@Override
	public HeldEntity create() {
		return new HeldEntity();
	}

	@Override
	public List<FieldMapper<HeldEntity, ?>> fieldMappers() {
		return Arrays.asList(ID, USER_ID, NAME, ACTIVE, DELETED, PUBLIC, GRUPPE, HKEY, LOCK_EXPIRE);
	}

	@Override
	public LongFieldMapper idField() {
		return ID;
	}
}
