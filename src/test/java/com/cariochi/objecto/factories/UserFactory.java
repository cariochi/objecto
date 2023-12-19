package com.cariochi.objecto.factories;


import com.cariochi.objecto.model.User;

public interface UserFactory extends BaseFactory, BaseUserGenerators {

    User user();

}
