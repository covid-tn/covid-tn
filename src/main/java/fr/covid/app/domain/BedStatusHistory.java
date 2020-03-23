package fr.covid.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A BedStatusHistory.
 */
@Document(collection = "bed_status_history")
public class BedStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @DBRef
    @Field("bed")
    private Bed bed;

    @DBRef
    @Field("createdBy")
    private User createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public BedStatusHistory createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Bed getBed() {
        return bed;
    }

    public BedStatusHistory bed(Bed bed) {
        this.bed = bed;
        return this;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public BedStatusHistory createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BedStatusHistory)) {
            return false;
        }
        return id != null && id.equals(((BedStatusHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BedStatusHistory{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
