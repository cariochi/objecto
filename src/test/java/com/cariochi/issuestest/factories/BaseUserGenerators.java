package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.PostProcessor;
import net.datafaker.Faker;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.replace;

public interface BaseUserGenerators {

    @PostProcessor
    private void userPostProcessor(User user) {
        final String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
        user.setUsername(username);
        user.setEmail(username + "@" + new Faker().internet().domainName());
    }

}
