package fr.covid.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

import fr.covid.app.domain.enumeration.BedStatus;

/**
 * A Bed.
 */
@Document(collection = "bed")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("status")
    private BedStatus status;

    @DBRef
    @Field("hospital")
    @JsonIgnoreProperties("beds")
    private Hospital hospital;

    @DBRef
    @Field("room")
    @JsonIgnoreProperties("beds")
    private ServiceRoom room;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BedStatus getStatus() {
        return status;
    }

    public Bed status(BedStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BedStatus status) {
        this.status = status;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Bed hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public ServiceRoom getRoom() {
        return room;
    }

    public Bed room(ServiceRoom serviceRoom) {
        this.room = serviceRoom;
        return this;
    }

    public void setRoom(ServiceRoom serviceRoom) {
        this.room = serviceRoom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bed)) {
            return false;
        }
        return id != null && id.equals(((Bed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bed{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
