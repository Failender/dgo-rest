package de.failender.dgo.rest.helden;

public class Steigerung {

    private String name;
    private int lehrmeisterTaw;
    private int ap;
    private int kostenHeller;
    private int modifier;
    private String tooltip;


    public Steigerung(String name, int lehrmeisterTaw, int ap, int kostenHeller, int modifier, String tooltip) {
        this.name = name;
        this.lehrmeisterTaw = lehrmeisterTaw;
        this.ap = ap;
        this.kostenHeller = kostenHeller;
        this.modifier = modifier;
        this.tooltip = tooltip;
    }

    public String getName() {
        return name;
    }

    public int getLehrmeisterTaw() {
        return lehrmeisterTaw;
    }

    public int getAp() {
        return ap;
    }

    public String getTooltip() {
        return tooltip;
    }

    public int getKostenHeller() {
        return kostenHeller;
    }

    public String getKostenDukaten() {
        return kostenHeller / 100 + "," + kostenHeller % 100;
    }

    public int getModifier() {
        return modifier;
    }
}
