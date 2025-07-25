package com.cariochi.issuestest.factories;


import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.Issue.Type;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Modify;

public interface IssueFactory extends BaseIssueFactory {

    @Modify("key=?")
    IssueFactory withKey(String key);

    @Modify("setType(?)")
    IssueFactory withType(Type type);

    @Modify("setStatus(?)")
    IssueFactory withStatus(Status status);

    IssueFactory withAssignee(@Modify("setAssignee(?)") User assignee);

    @Modify("comments[*].commenter=?")
    IssueFactory withAllCommenter(User commenter);

    @Modify("comments[*].setCommenter(?)")
    IssueFactory withAllCommenterByMethod(User commenter);

    @Modify("dependencies.put(?, ?)")
    IssueFactory withDependency(DependencyType type, Issue issue);

    @Modify("comments[?].commenter=?")
    IssueFactory withCommenter(int index, User commenter);

    @Modify("comments[100].commenter=?")
    IssueFactory withWrongCommenter(User commenter);

    @Modify("subtasks[*].status")
    IssueFactory withStatuses(Status status);

}
