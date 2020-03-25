package fr.covid.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.covid.app.domain.enumeration.BedStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Bed.
 */
 @Entity(name = "bed")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private BedStatus status;

    @JoinColumn(name = "hospital_id", nullable = false)
    @ManyToOne
    @JsonIgnoreProperties("beds")
    private Hospital hospital;

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
