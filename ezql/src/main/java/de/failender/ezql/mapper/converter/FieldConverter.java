package de.failender.ezql.mapper.converter;

public interface FieldConverter<FIELD> {

    String convertEntry(FIELD value);
}
