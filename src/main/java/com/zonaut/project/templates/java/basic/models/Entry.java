package com.zonaut.project.templates.java.basic.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * Mapped Entry entity class for keeping record of creation and modification dates.
 *
 * @author David Debuck
 */
@MappedSuperclass
public abstract class Entry implements Serializable {

    @NotNull
    @Column(name = "created_date", nullable = false, columnDefinition="DATETIME(6)")
    private Instant createdDate = Instant.now();

    @NotNull
    @Column(name = "last_modified_date", nullable = false, columnDefinition="DATETIME(6)")
    private Instant lastModifiedDate = Instant.now();

    // TODO
    // Check for postgres and so on ...

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
