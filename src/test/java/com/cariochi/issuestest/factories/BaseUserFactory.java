package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Faker.Base.Internet;
import com.cariochi.objecto.PostGenerate;
import com.cariochi.objecto.random.ObjectoRandom;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.replace;

public interface BaseUserFactory {

    @PostGenerate
    private void userPostProcessor(User user, ObjectoRandom random) {
        final String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
        user.setUsername(username);
        user.setEmail(username + "@" + random.strings().faker().nextString(Internet.DOMAIN_NAME));
    }

}
