package de.failender.dgo.rest.kampf;

public class Teilnehmer {
    private String name;
    private Integer id;
    private Integer iniBasis;
    private Integer iniWurf;
    private boolean atAktion = true;
    private boolean paAktion = true;
    private int freieAktionen = 1;
    private int iniMod;

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
        return iniBasis + iniWurf + iniMod;
    }

    public int getIniMod() {
        return iniMod;
    }

    public void setIniMod(int iniMod) {
        this.iniMod = iniMod;
    }

    public boolean isAtAktion() {
        return atAktion;
    }

    public void setAtAktion(boolean atAktion) {
        this.atAktion = atAktion;
    }

    public boolean isPaAktion() {
        return paAktion;
    }

    public void setPaAktion(boolean paAktion) {
        this.paAktion = paAktion;
    }

    public int getFreieAktionen() {
        return freieAktionen;
    }

    public void setFreieAktionen(int freieAktionen) {
        this.freieAktionen = freieAktionen;
    }

    public void resetAktionen() {
        atAktion = true;
        paAktion = true;
        freieAktionen = 1; //TODO More freie aktionen while high ini
    }
}
