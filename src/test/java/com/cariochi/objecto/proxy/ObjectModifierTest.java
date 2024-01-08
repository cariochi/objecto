package com.cariochi.objecto.proxy;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.factories.UserFactory;
import com.cariochi.objecto.issues.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectModifierTest {

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
