package com.cariochi.objecto.factories;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.model.User;
import com.cariochi.objecto.model.User.Fields;
import net.datafaker.Faker;

public interface BaseUserGenerators {

    @FieldGenerator(type = User.class, field = User.Fields.email)
    default String email() {
        return new Faker().internet().emailAddress();
    }

    @FieldGenerator(type = User.class, field = Fields.phone)
    default String phone() {
        return new Faker().phoneNumber().cellPhone();
    }

    @FieldGenerator(type = User.class, field = Fields.username)
    default String username() {
        return new Faker().name().username();
    }

    @FieldGenerator(type = User.class, field = Fields.fullName)
    default String fullName() {
        return new Faker().name().fullName();
    }

    @FieldGenerator(type = User.class, field = Fields.companyName)
    default String companyName() {
        return new Faker().company().name();
    }

}
