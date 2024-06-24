package com.saolasoft.websocket.persistence.model.interfaces;

import java.io.Serializable;
import java.util.Date;

/**
 * In iTech, data are rarely fully deleted (purged) from the system; rather, they are either
 * voided or retired. When data can be removed (effectively deleted from the user's perspective),
 * then they are voidable. Voided data are no longer valid and references from other non-voided data
 * are not valid. For example, when duplicate patient records are merged, the record that is not
 * kept is voided (invalidated).
 */
public interface VoidableEntity<ID extends Serializable> extends AuditableEntity<ID> {

    /**
     * @return Long - the ID of the user who voided the object
     */
    Long getVoidedBy();

    /**
     * @param voidedBy - the ID of the user who voided the object
     */
    void setVoidedBy(Long voidedBy);

    /**
     * @param dateVoided - the date the object was voided
     */
    void setDateVoided(Date dateVoided);

    /**
     * @return Date - the date the object was voided
     */
    Date getDateVoided();

    /**
     * @return String - the reason the object was voided
     */
    String getVoidReason();

    /**
     * @param voidReason - the reason the object was voided
     */
    void setVoidReason(String voidReason);

    /**
     * @return Boolean - whether of not this object is voided
     */
    Boolean getVoided();

    /**
     * @param voided - whether of not this object is voided
     */
    void setVoided(Boolean voided);
}
