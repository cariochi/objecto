package com.cariochi.objecto.factories;


import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.User;

public interface UserFactory extends BaseFactory, BaseUserGenerators {

    User createUser();

    @Modifier("username")
    UserFactory withUsername(String username);

}
