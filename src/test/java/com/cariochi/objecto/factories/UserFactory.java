package com.cariochi.objecto.factories;


import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.issues.model.User;

public abstract class UserFactory implements BaseFactory, BaseUserGenerators {

    public abstract User createUser();

    @Modifier("username=?")
    public abstract UserFactory withUsername(String username);

}
