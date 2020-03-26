package fr.covid.app.domain;

import fr.covid.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HospitalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospital.class);
        Hospital hospital1 = new Hospital();
        hospital1.setId(1l);
        Hospital hospital2 = new Hospital();
        hospital2.setId(hospital1.getId());
        assertThat(hospital1).isEqualTo(hospital2);
        hospital2.setId(1l);
        assertThat(hospital1).isNotEqualTo(hospital2);
        hospital1.setId(null);
        assertThat(hospital1).isNotEqualTo(hospital2);
    }
}
