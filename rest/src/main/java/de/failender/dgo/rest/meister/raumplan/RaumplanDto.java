package de.failender.dgo.rest.meister.raumplan;

import de.failender.dgo.persistance.meister.raumplan.RaumplanEntity;
import de.failender.dgo.rest.helden.HeldenController;

public class RaumplanDto {
    private String name;
    private String createdDate;
    private Long id;

    public RaumplanDto(RaumplanEntity entity) {
        this.name = entity.getName();
        this.createdDate = HeldenController.FORMATTER.format(entity.getCreatedDate());
        this.id = entity.getId();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
