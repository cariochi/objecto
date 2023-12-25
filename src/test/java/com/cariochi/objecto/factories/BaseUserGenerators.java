package com.cariochi.objecto.factories;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.model.User;
import com.cariochi.objecto.model.User.Fields;
import net.datafaker.Faker;

public interface BaseUserGenerators {

    @FieldGenerator(type = User.class, field = User.Fields.email)
    private String emailGenerator() {
        return new Faker().internet().emailAddress();
    }

    @FieldGenerator(type = User.class, field = Fields.phone)
    private String phoneGenerator() {
        return new Faker().phoneNumber().cellPhone();
    }

    @FieldGenerator(type = User.class, field = Fields.username)
    private String usernameGenerator() {
        return new Faker().name().username();
    }

    @FieldGenerator(type = User.class, field = Fields.fullName)
    private String fullNameGenerator() {
        return new Faker().name().fullName();
    }

    @FieldGenerator(type = User.class, field = Fields.companyName)
    private String companyNameGenerator() {
        return new Faker().company().name();
    }

}
