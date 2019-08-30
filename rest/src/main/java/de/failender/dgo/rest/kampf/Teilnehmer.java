package de.failender.dgo.rest.kampf;

public class Teilnehmer {
    private String name;
    private Integer id;
    private Integer iniBasis;
    private Integer iniWurf;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIniBasis() {
        return iniBasis;
    }

    public void setIniBasis(Integer iniBasis) {
        this.iniBasis = iniBasis;
    }

    public Integer getIniWurf() {
        return iniWurf;
    }

    public void setIniWurf(Integer iniWurf) {
        this.iniWurf = iniWurf;
    }

    public Integer getIni() {
        return iniBasis + iniWurf;
    }
}
