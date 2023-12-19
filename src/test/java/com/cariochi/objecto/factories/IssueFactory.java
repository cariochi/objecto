package com.cariochi.objecto.factories;


import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Param;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Fields;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public interface IssueFactory extends BaseIssueFactory, BaseUserGenerators {

    IssueFactory key(@Param("key") String key);

    IssueFactory type(@Param("type") Type type);

    IssueFactory status(@Param("status") Status status);

    IssueFactory assignee(@Param("assignee") User assignee);

    @FieldGenerator(type = Issue.class, field = Fields.labels)
    default List<String> labels() {
        Faker faker = new Faker();
        return List.of("LABEL1", faker.lorem().word().toUpperCase());
    }

}
