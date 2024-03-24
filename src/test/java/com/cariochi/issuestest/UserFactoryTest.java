package com.cariochi.issuestest;

import com.cariochi.issuestest.factories.UserFactory;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Objecto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserFactoryTest {

    private final UserFactory userFactory = Objecto.create(UserFactory.class);

    @Test
    void should_generate_default_user_with_modifier() {
        final User user = userFactory.withUsername("test").createDefaultUser();
        assertThat(user.getUsername()).isEqualTo("test");
    }
}
