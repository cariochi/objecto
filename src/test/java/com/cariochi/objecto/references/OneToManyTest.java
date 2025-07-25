package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.Reference;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneToManyTest {

    private final ObjectoFactory factory = Objecto.create(ObjectoFactory.class);

    @Test
    void test() {
        final Campaign campaign = factory.createParent();

        assertThat(campaign.getTactics())
                .extracting(Tactic::getCampaign)
                .containsOnly(campaign);

        final Tactic tactic = factory.createChild();

        assertThat(tactic.getCampaign().getTactics().get(0))
                .isEqualTo(tactic);
        assertThat(tactic.getCampaign().getTactics().get(1))
                .isNotEqualTo(tactic);

        assertThat(tactic.getCampaign().getTactics())
                .extracting(Tactic::getCampaign)
                .containsOnly(tactic.getCampaign());

    }

    private interface ObjectoFactory {

        @Reference("tactics[*].campaign")
        Campaign createParent();

        //        @Reference("campaign.tactics[*]")
        Tactic createChild();

    }

    @Data
    private static class Campaign {

        private String name;
        private List<Tactic> tactics;

    }

    @Data
    private static class Tactic {

        private String name;

        @ToString.Exclude
        private Campaign campaign;

    }

}
