package fr.covid.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.covid.app.web.rest.TestUtil;

public class ServiceRoomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRoom.class);
        ServiceRoom serviceRoom1 = new ServiceRoom();
        serviceRoom1.setId("id1");
        ServiceRoom serviceRoom2 = new ServiceRoom();
        serviceRoom2.setId(serviceRoom1.getId());
        assertThat(serviceRoom1).isEqualTo(serviceRoom2);
        serviceRoom2.setId("id2");
        assertThat(serviceRoom1).isNotEqualTo(serviceRoom2);
        serviceRoom1.setId(null);
        assertThat(serviceRoom1).isNotEqualTo(serviceRoom2);
    }
}
