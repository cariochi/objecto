package com.cariochi.objecto.factories;


import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public abstract class IssueAbstractFactory implements BaseIssueFactory, BaseUserGenerators {

    private final Faker faker = new Faker();

    @Modifier("key")
    public abstract IssueAbstractFactory withKey(String key);

    @Modifier("type")
    public abstract IssueAbstractFactory withType(Type type);

    @Modifier("status")
    public abstract IssueAbstractFactory withStatus(Status status);

    @Modifier("assignee")
    public abstract IssueAbstractFactory withAssignee(User assignee);

    @Modifier("comments[*].commenter")
    public abstract IssueAbstractFactory withAllCommenter(User commenter);

    @Modifier("comments[0].commenter")
    public abstract IssueAbstractFactory withFirstCommenter(User commenter);

    @Modifier("comments[100].commenter")
    public abstract IssueAbstractFactory withWrongCommenter(User commenter);

    @FieldGenerator(type = Issue.class, field = Issue.Fields.labels)
    private List<String> labelsGenerator() {
        return List.of("LABEL1", faker.lorem().word().toUpperCase());
    }


}
