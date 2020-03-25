package fr.covid.app.domain;

import fr.covid.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BedStatusHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedStatusHistory.class);
        BedStatusHistory bedStatusHistory1 = new BedStatusHistory();
        bedStatusHistory1.setId("id1");
        BedStatusHistory bedStatusHistory2 = new BedStatusHistory();
        bedStatusHistory2.setId(bedStatusHistory1.getId());
        assertThat(bedStatusHistory1).isEqualTo(bedStatusHistory2);
        bedStatusHistory2.setId("id2");
        assertThat(bedStatusHistory1).isNotEqualTo(bedStatusHistory2);
        bedStatusHistory1.setId(null);
        assertThat(bedStatusHistory1).isNotEqualTo(bedStatusHistory2);
    }
}
