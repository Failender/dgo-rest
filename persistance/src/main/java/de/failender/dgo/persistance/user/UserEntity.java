package de.failender.dgo.persistance.user;

import de.failender.dgo.persistance.BaseEntity;

public class UserEntity extends BaseEntity {

	private String name;
	private String password;
	private String token;
	private Long gruppe;
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

	public Long getGruppe() {
		return this.gruppe;
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

	public void setGruppe(final Long gruppe) {
		this.gruppe = gruppe;
	}

	public Boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}
}
