package com.saolasoft.websocket.persistence.model.interfaces;

import java.io.Serializable;

/**
 * This is the base interface for all object models
 */
public interface BaseEntity<ID extends Serializable> extends Serializable {

    /**
     * @return id - The unique Identifier for the object
     */
    ID getId();

    /**
     * @param id - The unique Identifier for the object
     */
    void setId(ID id);

}
