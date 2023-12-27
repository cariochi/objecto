package com.cariochi.objecto.factories;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.PostProcessor;
import com.cariochi.objecto.model.User;
import com.cariochi.objecto.model.User.Fields;
import net.datafaker.Faker;

public interface BaseUserGenerators {

    @FieldGenerator(type = User.class, field = Fields.fullName)
    private String fullNameGenerator() {
        return new Faker().name().fullName();
    }

    @FieldGenerator(type = User.class, field = Fields.phone)
    private String phoneGenerator() {
        return new Faker().phoneNumber().cellPhone();
    }

    @FieldGenerator(type = User.class, field = Fields.companyName)
    private String companyNameGenerator() {
        return new Faker().company().name();
    }

    private String toUsername(String fullName) {
        return fullName.toLowerCase().replace(".", "").replace(" ", ".");
    }

    @PostProcessor
    private void userPostProcessor(User user) {
        final String username = toUsername(user.getFullName());
        user.setUsername(username);
        user.setEmail(username + "@" + new Faker().internet().domainName());
    }

}
