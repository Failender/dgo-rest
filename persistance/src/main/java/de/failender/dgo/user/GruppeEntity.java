package de.failender.dgo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GRUPPEN")
public class GruppeEntity extends BaseEntity {
	private String name;
	@Column(name = "DATUM")
	private Integer datum;


	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "meisterGruppen")
	private List<UserEntity> meister;
	@Column(name ="IMAGE")
	private String image;


	public String getName() {
		return this.name;
	}

	public Integer getDatum() {
		return this.datum;
	}

	public List<UserEntity> getMeister() {
		return this.meister;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setDatum(final Integer datum) {
		this.datum = datum;
	}

	public void setMeister(final List<UserEntity> meister) {
		this.meister = meister;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}