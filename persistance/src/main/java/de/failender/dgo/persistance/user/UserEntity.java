package de.failender.dgo.persistance.user;

import de.failender.dgo.persistance.AuditingEntity;
import de.failender.dgo.persistance.gruppe.GruppeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
public class UserEntity extends AuditingEntity {
	@Column(name = "NAME")
	private String name;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "TOKEN")
	private String token;
	@JoinColumn(name = "GRUPPE_ID")
	@ManyToOne
	private GruppeEntity gruppe;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_TO_MEISTER", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "GRUPPE_ID")})
	private List<GruppeEntity> meisterGruppen;

	@Column(name = "CAN_WRITE")
	private Boolean canWrite;

	public String getName() {
		return this.name;
	}


	public String getPassword() {
		return this.password;
	}

	public String getToken() {
		return this.token;
	}

	public GruppeEntity getGruppe() {
		return this.gruppe;
	}

	public List<GruppeEntity> getMeisterGruppen() {
		return this.meisterGruppen;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public void setGruppe(final GruppeEntity gruppe) {
		this.gruppe = gruppe;
	}

	public void setMeisterGruppen(final List<GruppeEntity> meisterGruppen) {
		this.meisterGruppen = meisterGruppen;
	}

	public Boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}
}
