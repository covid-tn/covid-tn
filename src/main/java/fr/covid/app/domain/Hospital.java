package fr.covid.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hospital.
 */
@Document(collection = "hospital")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @DBRef
    @Field("address")
    private Address address;

    @DBRef
    @Field("headOfSearvice")
    private User headOfSearvice;

    @DBRef
    @Field("bed")
    private Set<Bed> beds = new HashSet<>();

    @DBRef
    @Field("service")
    private Set<ServiceHospital> services = new HashSet<>();

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

    public Hospital name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public Hospital address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getHeadOfSearvice() {
        return headOfSearvice;
    }

    public Hospital headOfSearvice(User user) {
        this.headOfSearvice = user;
        return this;
    }

    public void setHeadOfSearvice(User user) {
        this.headOfSearvice = user;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public Hospital beds(Set<Bed> beds) {
        this.beds = beds;
        return this;
    }

    public Hospital addBed(Bed bed) {
        this.beds.add(bed);
        bed.setHospital(this);
        return this;
    }

    public Hospital removeBed(Bed bed) {
        this.beds.remove(bed);
        bed.setHospital(null);
        return this;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }

    public Set<ServiceHospital> getServices() {
        return services;
    }

    public Hospital services(Set<ServiceHospital> serviceHospitals) {
        this.services = serviceHospitals;
        return this;
    }

    public Hospital addService(ServiceHospital serviceHospital) {
        this.services.add(serviceHospital);
        serviceHospital.setHospital(this);
        return this;
    }

    public Hospital removeService(ServiceHospital serviceHospital) {
        this.services.remove(serviceHospital);
        serviceHospital.setHospital(null);
        return this;
    }

    public void setServices(Set<ServiceHospital> serviceHospitals) {
        this.services = serviceHospitals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return id != null && id.equals(((Hospital) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
