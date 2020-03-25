package fr.covid.app.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Hospital.
 */
@Entity(name = "hospital")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hospital_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;

    @NotNull
    @Column(name = "name")
    private String name;

    @JoinColumn
    @Column(name = "address")
    private Address address;

    @JoinColumn
    @Column(name = "headOfService")
    private User headOfService;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private Set<Bed> beds = new HashSet<>();

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

    public User getHeadOfService() {
        return headOfService;
    }

    public Hospital headOfSearvice(User user) {
        this.headOfService = user;
        return this;
    }

    public void setHeadOfService(User user) {
        this.headOfService = user;
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
