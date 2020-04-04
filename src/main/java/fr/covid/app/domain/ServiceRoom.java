package fr.covid.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceRoom.
 */
@Document(collection = "service_room")
public class ServiceRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @DBRef
    @Field("bed")
    private Set<Bed> beds = new HashSet<>();

    @DBRef
    @Field("serviceHospital")
    @JsonIgnoreProperties("rooms")
    private ServiceHospital serviceHospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceRoom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ServiceRoom description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public ServiceRoom beds(Set<Bed> beds) {
        this.beds = beds;
        return this;
    }

    public ServiceRoom addBed(Bed bed) {
        this.beds.add(bed);
        bed.setRoom(this);
        return this;
    }

    public ServiceRoom removeBed(Bed bed) {
        this.beds.remove(bed);
        bed.setRoom(null);
        return this;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }

    public ServiceHospital getServiceHospital() {
        return serviceHospital;
    }

    public ServiceRoom serviceHospital(ServiceHospital serviceHospital) {
        this.serviceHospital = serviceHospital;
        return this;
    }

    public void setServiceHospital(ServiceHospital serviceHospital) {
        this.serviceHospital = serviceHospital;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRoom)) {
            return false;
        }
        return id != null && id.equals(((ServiceRoom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceRoom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
