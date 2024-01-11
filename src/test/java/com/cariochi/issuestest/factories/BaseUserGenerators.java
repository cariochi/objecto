package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.User;
import com.cariochi.issuestest.model.User.Fields;
import com.cariochi.objecto.Generator;
import com.cariochi.objecto.PostProcessor;
import net.datafaker.Faker;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.replace;

public interface BaseUserGenerators {

    @Generator(type = User.class, expression = Fields.fullName)
    private String fullNameGenerator() {
        return new Faker().name().fullName();
    }

    @Generator(type = User.class, expression = Fields.phone)
    private String phoneGenerator() {
        return new Faker().phoneNumber().cellPhone();
    }

    @Generator(type = User.class, expression = Fields.companyName)
    private String companyNameGenerator() {
        return new Faker().company().name();
    }

    @PostProcessor
    private void userPostProcessor(User user) {
        final String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
        user.setUsername(username);
        user.setEmail(username + "@" + new Faker().internet().domainName());
    }

}
