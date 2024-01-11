package com.cariochi.objecto.proxy;

import com.cariochi.issuestest.factories.UserFactory;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Objecto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectoModifierTest {

    private final UserFactory userFactory = Objecto.create(UserFactory.class);

    @Test
    void should_modify_object() {
        final User user = userFactory.createUser();
        final User modified = ((ObjectModifier) userFactory.withUsername("TEST USER")).modifyObject(user);
        assertThat(modified)
                .extracting(User::getUsername)
                .isEqualTo("TEST USER");
    }

}
