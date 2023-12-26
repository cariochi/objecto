package com.cariochi.objecto.factories;


import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;

public interface IssueFactory extends BaseIssueFactory {

    @Modifier("key")
    IssueFactory withKey(String key);

    @Modifier("type")
    IssueFactory withType(Type type);

    @Modifier("status")
    IssueFactory withStatus(Status status);

    @Modifier("assignee")
    IssueFactory withAssignee(User assignee);

}
