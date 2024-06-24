package com.saolasoft.websocket.persistence.model.interfaces;

import java.io.Serializable;
import java.util.Date;

/**
 * In iTech, the convention is to track basic audit information for each object related to who
 * initially created the object and when, and who last changed the object and when. This allows us
 * to check, for example, when a patient record was created, or when a person address was last
 * updated in the system. Any object that needs to keep track of this information should implement
 * this interface.
 */
public interface AuditableEntity<ID extends Serializable> extends BaseEntity<ID> {

    /**
     * @return ID - the identifier of the user who created the object
     */
    Long getCreator();

    /**
     * @param creatorId - the identifier of the user who created the object
     */
    void setCreator(Long creatorId);

    /**
     * @return Date - the date the object was created
     */
    Date getDateCreated();

    /**
     * @param dateCreated - the date the object was created
     */
    void setDateCreated(Date dateCreated);

    /**
     * @return ID - the identifier of the user who updated the object
     */
    Long getUpdater();

    /**
     * @param updaterId - the identifier of the user who updated the object
     */
    void setUpdater(Long updaterId);

    /**
     * @return Date - the date the object was updated
     */
    Date getDateUpdated();

    /**
     * @param dateUpdated - the date the object was updated
     */
    void setDateUpdated(Date dateUpdated);

}
