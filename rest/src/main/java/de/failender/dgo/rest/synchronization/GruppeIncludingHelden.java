// Generated by delombok at Thu Nov 22 18:54:11 CET 2018
package de.failender.dgo.rest.synchronization;


import java.util.List;

public class GruppeIncludingHelden {
	private String name;
	private int id;
	private List<HeldenInfo> helden;


	public String getName() {
		return this.name;
	}


	public int getId() {
		return this.id;
	}


	public List<HeldenInfo> getHelden() {
		return this.helden;
	}


	public void setName(final String name) {
		this.name = name;
	}


	public void setId(final int id) {
		this.id = id;
	}


	public void setHelden(final List<HeldenInfo> helden) {
		this.helden = helden;
	}
}