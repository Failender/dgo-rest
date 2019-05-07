package de.failender.ezql.mapper;

import java.util.List;

public abstract class EntityMapper<T> {

	public abstract String table();
	public abstract T create();
	public abstract Class<T> entityClass();
	public abstract List<FieldMapper<T, ?>> fieldMappers();
	public abstract IntFieldMapper idField();

	public static <T> T firstOrNull(List<T> list) {
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
