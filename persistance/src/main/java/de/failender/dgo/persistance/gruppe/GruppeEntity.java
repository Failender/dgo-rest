package de.failender.dgo.persistance.gruppe;

import de.failender.dgo.persistance.BaseEntity;



public class GruppeEntity extends BaseEntity {
	private String name;
	private Integer datum;

	public String getName() {
		return this.name;
	}

	public Integer getDatum() {
		return this.datum;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setDatum(final Integer datum) {
		this.datum = datum;
	}

}
