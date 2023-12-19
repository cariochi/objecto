package com.cariochi.objecto.factories;


import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Param;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public abstract class IssueAbstractFactory implements BaseIssueFactory, BaseUserGenerators {

    private final Faker faker = new Faker();

    public abstract IssueAbstractFactory key(@Param("key") String key);

    public abstract IssueAbstractFactory type(@Param("type") Type type);

    public abstract IssueAbstractFactory status(@Param("status") Status status);

    public abstract IssueAbstractFactory assignee(@Param("assignee") User assignee);

    public abstract IssueAbstractFactory allCommenter(@Param("comments[*].commenter") User commenter);

    public abstract IssueAbstractFactory firstCommenter(@Param("comments[0].commenter") User commenter);

    public abstract IssueAbstractFactory wrongCommenter(@Param("comments[100].commenter") User commenter);

    @FieldGenerator(type = Issue.class, field = Issue.Fields.labels)
    private List<String> labels() {
        return List.of("LABEL1", faker.lorem().word().toUpperCase());
    }


}
