// Generated by delombok at Thu Nov 22 18:54:11 CET 2018
package de.failender.dgo.rest.synchronization;


public class GruppeInfo {
	private String name;
	private int id;
	private boolean isMeister;
	private Integer datum;
	private boolean isUserGroup;
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMeister() {
		return isMeister;
	}

	public void setMeister(boolean meister) {
		isMeister = meister;
	}

	public Integer getDatum() {
		return datum;
	}

	public void setDatum(Integer datum) {
		this.datum = datum;
	}

	public boolean isUserGroup() {
		return isUserGroup;
	}

	public void setUserGroup(boolean userGroup) {
		isUserGroup = userGroup;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
