package de.failender.dgo.persistance.asset;

import de.failender.dgo.persistance.BaseEntity;

public class AssetEntity extends BaseEntity {

    private String name;
    private Long gruppe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGruppe() {
        return gruppe;
    }

    public void setGruppe(Long gruppe) {
        this.gruppe = gruppe;
    }
}
