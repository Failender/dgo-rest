package de.failender.dgo.persistance.held;

import de.failender.dgo.persistance.user.UserEntity;
import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

public class VersionMapper extends EntityMapper<VersionEntity> {

	public static final VersionMapper INSTANCE = new VersionMapper();

	public static final LongFieldMapper<VersionEntity> ID = new LongFieldMapper<>("ID", VersionEntity::setId, VersionEntity::getId);
	public static final LongFieldMapper<VersionEntity> HELD_ID = new LongFieldMapper<>("HELDID", VersionEntity::setHeldid, VersionEntity::getHeldid);
	public static final IntFieldMapper<VersionEntity> VERSION = new IntFieldMapper<>("VERSION", VersionEntity::setVersion, VersionEntity::getVersion);
	public static final StringFieldMapper<VersionEntity> LAST_EVENT = new StringFieldMapper<>("LAST_EVENT", VersionEntity::setLastEvent, VersionEntity::getLastEvent);
	public static final UUIDFieldMapper<VersionEntity> CACHE_ID = new UUIDFieldMapper<>("CACHE_ID", VersionEntity::setCacheId, VersionEntity::getCacheId);
	public static final LocalDateTimeFieldMapper<VersionEntity> CREATED_DATE = new LocalDateTimeFieldMapper<>("CREATED_DATE", VersionEntity::setCreatedDate, VersionEntity::getCreatedDate);
	public static final IntFieldMapper<VersionEntity> AP = new IntFieldMapper<>("AP", VersionEntity::setAp, VersionEntity::getAp);



	@Override
	public String table() {
		return "HELD_VERSION";
	}

	@Override
	public VersionEntity create() {
		return new VersionEntity();
	}

	@Override
	public List<FieldMapper<VersionEntity, ?>> fieldMappers() {
		return Arrays.asList(ID, HELD_ID, VERSION, LAST_EVENT, CACHE_ID, AP, CREATED_DATE);
	}

	@Override
	public LongFieldMapper idField() {
		return ID;
	}
}
