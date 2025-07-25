package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.DefaultGenerator;
import com.cariochi.objecto.Faker;
import com.cariochi.objecto.Faker.Base.Company;
import com.cariochi.objecto.Faker.Base.Name;
import com.cariochi.objecto.Faker.Base.PhoneNumber;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.UseFactory;

@UseFactory(DatesFactory.class)
public abstract class UserFactory implements BaseUserFactory {

    @DefaultGenerator
    @Faker(field = "fullName", expression = Name.FULL_NAME)
    @Faker(field = "phone", expression = PhoneNumber.CELL_PHONE)
    @Faker(field = "companyName", expression = Company.NAME)
    public abstract User createUser();

    @Modify("username=?")
    public abstract UserFactory withUsername(String username);

    public User createDefaultUser() {
        return User.builder().build();
    }

}
