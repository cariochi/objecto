package com.cariochi.objecto;

import com.cariochi.objecto.DatafakerMethod.Address;
import com.cariochi.objecto.DatafakerMethod.Avatar;
import com.cariochi.objecto.DatafakerMethod.Book;
import com.cariochi.objecto.DatafakerMethod.Brand;
import com.cariochi.objecto.DatafakerMethod.Business;
import com.cariochi.objecto.DatafakerMethod.Color;
import com.cariochi.objecto.DatafakerMethod.Commerce;
import com.cariochi.objecto.DatafakerMethod.Company;
import com.cariochi.objecto.DatafakerMethod.Computer;
import com.cariochi.objecto.DatafakerMethod.Currency;
import com.cariochi.objecto.DatafakerMethod.Device;
import com.cariochi.objecto.DatafakerMethod.File;
import com.cariochi.objecto.DatafakerMethod.Finance;
import com.cariochi.objecto.DatafakerMethod.Image;
import com.cariochi.objecto.DatafakerMethod.Internet;
import com.cariochi.objecto.DatafakerMethod.Lorem;
import com.cariochi.objecto.DatafakerMethod.Medical;
import com.cariochi.objecto.DatafakerMethod.Name;
import com.cariochi.objecto.DatafakerMethod.PhoneNumber;
import com.cariochi.objecto.DatafakerMethod.TimeAndDate;
import com.cariochi.objecto.DatafakerMethod.Vehicle;
import com.cariochi.objecto.DatafakerMethod.Yoda;
import com.cariochi.reflecto.fields.ReflectoField;
import com.cariochi.reflecto.fields.TargetField;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.cariochi.reflecto.Reflecto.reflect;
import static java.util.stream.Collectors.toList;

public class DatafakerConstantsTest {

    @Test
    void test() {
        ObjectoRandom random = new ObjectoRandom();
        testConstants(Name.class, random);
        testConstants(Address.class, random);
        testConstants(TimeAndDate.class, random);
        testConstants(Avatar.class, random);
        testConstants(Brand.class, random);
        testConstants(Business.class, random);
        testConstants(Color.class, random);
        testConstants(Computer.class, random);
        testConstants(Currency.class, random);
        testConstants(Device.class, random);
        testConstants(File.class, random);
        testConstants(Image.class, random);
        testConstants(Lorem.class, random);
        testConstants(Company.class, random);
        testConstants(PhoneNumber.class, random);
        testConstants(Internet.class, random);
        testConstants(Finance.class, random);
        testConstants(Commerce.class, random);
        testConstants(Medical.class, random);
        testConstants(Book.class, random);
        testConstants(Vehicle.class, random);
        testConstants(Yoda.class, random);
    }

    private List<String> testConstants(Type type, ObjectoRandom random) {
        return reflect(type).fields().stream()
                .map(ReflectoField::asStatic)
                .map(TargetField::getValue)
                .map(String.class::cast)
                .map(random::nextDatafakerString)
                .collect(toList());
    }
}
