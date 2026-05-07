package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.Datafaker.Base.Company;
import com.cariochi.objecto.Datafaker.Base.Name;
import com.cariochi.objecto.Datafaker.Base.PhoneNumber;
import com.cariochi.objecto.ImportFactory;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.PrimaryGenerator;

@ImportFactory(DatesFactory.class)
public abstract class UserFactory implements BaseUserFactory {

    @PrimaryGenerator
    @Datafaker(field = "fullName", expression = Name.FULL_NAME)
    @Datafaker(field = "phone", expression = PhoneNumber.CELL_PHONE)
    @Datafaker(field = "companyName", expression = Company.NAME)
    public abstract User createUser();

    @Modify("username=?")
    public abstract UserFactory withUsername(String username);

    public User createDefaultUser() {
        return User.builder().build();
    }

}
