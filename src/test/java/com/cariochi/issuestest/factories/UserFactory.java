package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Modifier;

public abstract class UserFactory implements BaseFactory, BaseUserGenerators {

    public abstract User createUser();

    @Modifier("username=?")
    public abstract UserFactory withUsername(String username);

    public User createDefaultUser() {
        return User.builder().build();
    }

}
