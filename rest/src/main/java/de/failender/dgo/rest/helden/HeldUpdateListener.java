package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;

// interface to listen for all updates that can happen to helden
public interface HeldUpdateListener {

    void updated(HeldEntity heldEntity);

    void publicUpdated(HeldEntity heldEntity);

    void activeUpdated(HeldEntity heldEntity);
}
