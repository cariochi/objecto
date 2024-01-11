package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.Issue.Type;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Modifier;

public interface IssueFactory extends BaseIssueFactory {

    @Modifier("key=?")
    IssueFactory withKey(String key);

    @Modifier("setType(?)")
    IssueFactory withType(Type type);

    @Modifier("setStatus(?)")
    IssueFactory withStatus(Status status);

    IssueFactory withAssignee(@Modifier("setAssignee(?)") User assignee);

    @Modifier("comments[*].commenter=?")
    IssueFactory withAllCommenter(User commenter);

    @Modifier("comments[*].setCommenter(?)")
    IssueFactory withAllCommenterByMethod(User commenter);

    @Modifier("dependencies.put(?, ?)")
    IssueFactory withDependency(DependencyType type, Issue issue);

    @Modifier("comments[?].commenter=?")
    IssueFactory withCommenter(int index, User commenter);

    @Modifier("comments[100].commenter=?")
    IssueFactory withWrongCommenter(User commenter);

}
