package com.cariochi.issuestest;


import com.cariochi.issuestest.factories.BaseIssueFactory;
import com.cariochi.issuestest.factories.IssueFactory;
import com.cariochi.issuestest.factories.UserFactory;
import com.cariochi.issuestest.model.Attachment;
import com.cariochi.issuestest.model.Comment;
import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Properties;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.Issue.Type;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Objecto;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.cariochi.issuestest.model.Issue.Status.CLOSED;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

class IssueFactoryTest {

    private final IssueFactory issueFactory = Objecto.create(IssueFactory.class);
    private final UserFactory userFactory = Objecto.create(UserFactory.class);

    @Test
    void should_generate_issue() {
        final Issue issue = issueFactory.createIssue();
        assertThat(issue).isNotNull();

        // Type Constructors
        assertThat(issue.getAttachments())
                .extracting(Attachment::getFileContent)
                .doesNotContainNull();

        // Field Generators
        assertThat(issue.getKey()).startsWith("ID-");
        assertThat(issue.getComments())
                .extracting(Comment::getCommenter)
                .extracting(User::getFullName)
                .containsOnly("Vadym Deineka");
        assertThat(issue.getLabels().get(0)).isEqualTo(issue.getKey());
        assertThat(issue.getLabels().get(1)).isEqualTo("LABEL1");
        assertThat(issue.getAssignee().getEmail()).contains("@");
        assertThat(issue.getProperties())
                .extracting(Properties::getValue, Properties::getSize)
                .containsOnly("PROP", 101);

        // Type Generators
        final Instant expected = LocalDateTime.of(1978, Month.FEBRUARY, 20, 12, 0).atZone(UTC).toInstant();
        assertThat(issue.getCreationDate())
                .isEqualTo(expected);
        assertThat(issue.getComments())
                .extracting(Comment::getDate)
                .containsOnly(expected);
    }

    @Test
    void should_generate_list() {
        assertThat(issueFactory.createIssues())
                .isNotEmpty();

        assertThat(issueFactory.createIssues(Type.BUG))
                .isNotEmpty()
                .extracting(Issue::getType)
                .containsOnly(Type.BUG);

        assertThat(issueFactory.withType(Type.BUG).createIssues())
                .isNotEmpty()
                .extracting(Issue::getType)
                .containsOnly(Type.BUG);
    }

    @Test
    void should_generate_issue_with_parameters() {
        final Type type = Type.BUG;
        final User assignee = userFactory.createUser();

        assertThat(issueFactory.createIssue(type))
                .extracting(Issue::getType)
                .isEqualTo(type);

        assertThat(issueFactory.createIssue(assignee))
                .extracting(Issue::getAssignee)
                .isEqualTo(assignee);
    }

    @Test
    void should_generate_issue_with_modifiers() {
        final String key = "MY-KEY";
        final Type type = Type.BUG;
        final Status status = CLOSED;
        final User assignee = userFactory.createUser();

        final BaseIssueFactory modifiedFactory = issueFactory
                .withKey(key)
                .withType(type)
                .withStatus(status)
                .withAssignee(assignee);

        assertThat(modifiedFactory.createIssue())
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus, Issue::getAssignee)
                .containsExactly(key, type, status, assignee);

        assertThat(modifiedFactory.createDefaultIssue())
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus, Issue::getAssignee)
                .containsExactly(key, type, status, assignee);
    }

    @Test
    void should_get_default_issue() {
        final Issue issue = issueFactory.createDefaultIssue();
        assertThat(issue)
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus)
                .containsExactly("DEFAULT", Type.STORY, Status.OPEN);
    }

    @Test
    void should_modify_simple_complex_paths() {
        final User commenter = userFactory.createUser();

        assertThat(issueFactory.withCommenter(0, commenter).createIssue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);

        assertThat(issueFactory.withCommenter(0, commenter).createDefaultIssue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);
    }

    @Test
    void should_modify_complex_paths() {
        final User commenter = userFactory.createUser();

        assertThat(issueFactory.withAllCommenter(commenter).createIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);

        assertThat(issueFactory.withAllCommenter(commenter).createDefaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);
    }

    @Test
    void should_modify_paths_with_method_call() {
        final User commenter = userFactory.createUser();

        assertThat(issueFactory.withAllCommenterByMethod(commenter).createIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);

        assertThat(issueFactory.withAllCommenterByMethod(commenter).createDefaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);
    }

    @Test
    void should_modify_paths_with_method_with_multiple_parameters() {
        assertThat(issueFactory.withDependency(DependencyType.BLOCK, new Issue("BLOCK")).createIssue().getDependencies())
                .containsEntry(DependencyType.BLOCK, new Issue("BLOCK"));

        assertThat(issueFactory.withDependency(DependencyType.BLOCK, new Issue("BLOCK")).createDefaultIssue().getDependencies())
                .containsEntry(DependencyType.BLOCK, new Issue("BLOCK"));

        assertThat(issueFactory.createIssuesWithDependency(DependencyType.BLOCK, new Issue("BLOCK")).getDependencies())
                .containsEntry(DependencyType.BLOCK, new Issue("BLOCK"));
    }

    @Test
    void should_not_fail_on_wrong_complex_paths() {
        final User commenter = userFactory.createUser();

        assertThat(issueFactory.withWrongCommenter(commenter).createIssue().getComments())
                .extracting(Comment::getCommenter)
                .doesNotContain(commenter);

        assertThat(issueFactory.withWrongCommenter(commenter).createDefaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .doesNotContain(commenter);
    }

    @Test
    void should_use_post_processors() {
        final Issue issue = issueFactory.createIssue();

        assertThat(issue.getAssignee().getEmail())
                .startsWith(issue.getAssignee().getUsername());

        assertThat(issue.getLabels().get(0)).isEqualTo(issue.getKey());
    }

    @Test
    void should_allow_null_for_modifiers() {
        final List<Issue> issues = issueFactory.withType(null).createIssues();
        assertThat(issues)
                .extracting(Issue::getType)
                .containsOnlyNulls();
    }

    @Test
    void should_manage_bidirectional_references() {
        final Issue issue = issueFactory.createIssue();

        assertThat(issue.getSubtasks())
                .extracting(Issue::getParent)
                .containsOnly(issue);

        assertThat(issue.getParent().getSubtasks())
                .hasSizeGreaterThan(1)
                .containsOnlyOnce(issue);

    }

    @Test
    void should_set_subtask_statuses() {
        final Issue issue = issueFactory.withStatuses(CLOSED).createIssue();

        assertThat(issue.getSubtasks())
                .extracting(Issue::getStatus)
                .containsOnly(CLOSED);
    }

}
