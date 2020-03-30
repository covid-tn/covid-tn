package fr.covid.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Profile.
 */
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 5)
    @Field("pin")
    private String pin;

    @DBRef
    @Field("hospital")
    @JsonIgnoreProperties("profiles")
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getPin() {
        return pin;
    }

    public Profile pin(String pin) {
        this.pin = pin;
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Profile hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            ", pin='" + getPin() + "'" +
            "}";
    }
}
