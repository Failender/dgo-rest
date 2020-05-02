package de.failender.fantasygrounds;

public class Character {

    private String name;
    private Integer currentLep;
    private Integer currentAsp;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentLep() {
        return currentLep;
    }

    public void setCurrentLep(Integer currentLep) {
        this.currentLep = currentLep;
    }

    public Integer getCurrentAsp() {
        return currentAsp;
    }

    public void setCurrentAsp(Integer currentAsp) {
        this.currentAsp = currentAsp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
