package de.failender.dgo.persistance.gruppe;

import de.failender.ezql.mapper.*;

import java.util.Arrays;
import java.util.List;

public class GruppeMapper extends EntityMapper<GruppeEntity> {

	public static final GruppeMapper INSTANCE = new GruppeMapper();

	public static final LongFieldMapper<GruppeEntity> ID = new LongFieldMapper<>("ID", GruppeEntity::setId, GruppeEntity::getId);
	public static final StringFieldMapper<GruppeEntity> NAME = new StringFieldMapper<>("NAME", GruppeEntity::setName, GruppeEntity::getName);
	public static final IntFieldMapper<GruppeEntity> DATUM = new IntFieldMapper<>("DATUM", GruppeEntity::setDatum, GruppeEntity::getDatum);

	@Override
	public String table() {
		return "GRUPPEN";
	}

	@Override
	public GruppeEntity create() {
		return new GruppeEntity();
	}

	@Override
	public List<FieldMapper<GruppeEntity, ?>> fieldMappers() {
		return Arrays.asList(ID, NAME, DATUM);
	}

	@Override
	public LongFieldMapper idField() {
		return ID;
	}

}
