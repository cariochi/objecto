package com.cariochi.objecto.factories;


import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import net.datafaker.Faker;

public abstract class IssueAbstractFactory implements BaseIssueFactory {

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

}
