package fr.covid.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.covid.app.web.rest.TestUtil;

public class ServiceHospitalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceHospital.class);
        ServiceHospital serviceHospital1 = new ServiceHospital();
        serviceHospital1.setId("id1");
        ServiceHospital serviceHospital2 = new ServiceHospital();
        serviceHospital2.setId(serviceHospital1.getId());
        assertThat(serviceHospital1).isEqualTo(serviceHospital2);
        serviceHospital2.setId("id2");
        assertThat(serviceHospital1).isNotEqualTo(serviceHospital2);
        serviceHospital1.setId(null);
        assertThat(serviceHospital1).isNotEqualTo(serviceHospital2);
    }
}
