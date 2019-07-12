package de.failender.dgo.persistance.meister.raumplan;

import de.failender.dgo.persistance.BaseEntity;

import java.time.LocalDateTime;

public class RaumplanEntity extends BaseEntity {

    private String name;
    private Long owner;
    private LocalDateTime createdDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
