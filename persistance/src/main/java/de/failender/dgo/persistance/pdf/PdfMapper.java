package de.failender.dgo.persistance.pdf;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;
import de.failender.ezql.mapper.StringFieldMapper;

import java.util.Arrays;
import java.util.List;

public class PdfMapper extends EntityMapper<PdfEntity> {

	public static final LongFieldMapper<PdfEntity> ID = new LongFieldMapper<>("ID", PdfEntity::setId, PdfEntity::getId);
	public static final StringFieldMapper<PdfEntity> NAME = new StringFieldMapper<>("NAME", PdfEntity::setName, PdfEntity::getName);

	public static final PdfMapper INSTANCE = new PdfMapper();

	@Override
	public String table() {
		return "PDFS";
	}

	@Override
	public PdfEntity create() {
		return new PdfEntity();
	}

	@Override
	public List<FieldMapper<PdfEntity, ?>> fieldMappers() {
		return Arrays.asList(ID, NAME);
	}

	@Override
	public LongFieldMapper idField() {
		return ID;
	}
}
