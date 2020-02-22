package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.dgo.persistance.BaseEntity;

public class GegenstandToLagerortEntity extends BaseEntity {

    private long lagerort;
    private String name;
    private long amount;

    public long getLagerort() {
        return lagerort;
    }

    public void setLagerort(long lagerort) {
        this.lagerort = lagerort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
