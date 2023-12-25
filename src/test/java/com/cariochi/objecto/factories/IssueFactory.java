package com.cariochi.objecto.factories;


import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Fields;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public interface IssueFactory extends BaseIssueFactory {

    @Modifier("key")
    IssueFactory withKey(String key);

    @Modifier("type")
    IssueFactory withType(Type type);

    @Modifier("status")
    IssueFactory withStatus(Status status);

    @Modifier("assignee")
    IssueFactory withAssignee(User assignee);

    @FieldGenerator(type = Issue.class, field = Fields.labels)
    private List<String> labelsGenerator() {
        return List.of("LABEL1", new Faker().lorem().word().toUpperCase());
    }

}
