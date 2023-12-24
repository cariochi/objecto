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
    public abstract IssueAbstractFactory key(String key);

    @Modifier("type")
    public abstract IssueAbstractFactory type(Type type);

    @Modifier("status")
    public abstract IssueAbstractFactory status(Status status);

    @Modifier("assignee")
    public abstract IssueAbstractFactory assignee(User assignee);

    @Modifier("comments[*].commenter")
    public abstract IssueAbstractFactory allCommenter(User commenter);

    @Modifier("comments[0].commenter")
    public abstract IssueAbstractFactory firstCommenter(User commenter);

    @Modifier("comments[100].commenter")
    public abstract IssueAbstractFactory wrongCommenter(User commenter);

    @FieldGenerator(type = Issue.class, field = Issue.Fields.labels)
    private List<String> labels() {
        return List.of("LABEL1", faker.lorem().word().toUpperCase());
    }


}
