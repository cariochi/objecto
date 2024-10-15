package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.DatafakerMethod;
import com.cariochi.objecto.Fields;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.TypeFactory;

public abstract class UserFactory implements BaseFactory, BaseUserGenerators {

    @TypeFactory
    @Fields.Datafaker(field = "fullName", method = DatafakerMethod.Name.FullName)
    @Fields.Datafaker(field = "phone", method = DatafakerMethod.PhoneNumber.CellPhone)
    @Fields.Datafaker(field = "companyName", method = DatafakerMethod.Company.Name)
    public abstract User createUser();

    @Modifier("username=?")
    public abstract UserFactory withUsername(String username);

    public User createDefaultUser() {
        return User.builder().build();
    }

}
