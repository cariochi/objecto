package com.cariochi.objecto.factories;


import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.TypeGenerator;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Fields;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public interface IssueFactory extends BaseIssueFactory, BaseUserGenerators {

    @Modifier("key")
    IssueFactory key(String key);

    @Modifier("type")
    IssueFactory type(Type type);

    @Modifier("status")
    IssueFactory status(Status status);

    @Modifier("assignee")
    IssueFactory assignee(User assignee);

    @FieldGenerator(type = Issue.class, field = Fields.labels)
    private List<String> labels() {
        return List.of("LABEL1", new Faker().lorem().word().toUpperCase());
    }

    @TypeGenerator
    private String strings() {
        return new Faker().lorem().sentence();
    }

}
