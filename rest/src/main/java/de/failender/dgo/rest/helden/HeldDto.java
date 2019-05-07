package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;

public class HeldDto {

    private String name;
    private Long id;
    private String gruppe;
    private boolean isPublic;
    private boolean active;

    public HeldDto(HeldEntity heldEntity, String gruppe) {
        this.name = heldEntity.getName();
        this.id = heldEntity.getId();
        this.gruppe = gruppe;
        this.isPublic = heldEntity.isPublic();
        this.active = heldEntity.isActive();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getGruppe() {
        return gruppe;
    }
}
