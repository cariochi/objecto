package com.cariochi.objecto;


import com.cariochi.objecto.factories.IssueAbstractFactory;
import com.cariochi.objecto.factories.UserFactory;
import com.cariochi.objecto.model.Attachment;
import com.cariochi.objecto.model.Comment;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractClassFactoryTest {

    private final IssueAbstractFactory issueFactory = Objecto.create(IssueAbstractFactory.class);
    private final UserFactory userFactory = Objecto.create(UserFactory.class);

    @Test
    void should_generate_issue() {
        final Issue issue = issueFactory.issue();
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
        assertThat(issue.getLabels().get(0)).isEqualTo("LABEL1");
        assertThat(issue.getAssignee().getEmail()).contains("@");

        // Type Generators
        final Instant expected = LocalDateTime.of(1978, Month.FEBRUARY, 20, 12, 0).atZone(UTC).toInstant();
        assertThat(issue.getCreationDate())
                .isEqualTo(expected);
        assertThat(issue.getComments())
                .extracting(Comment::getDate)
                .containsOnly(expected);
    }

    @Test
    void should_generate_issue_with_parameters() {
        final Type type = Type.BUG;
        final User assignee = userFactory.user();

        assertThat(issueFactory.issue(type))
                .extracting(Issue::getType)
                .isEqualTo(type);

        assertThat(issueFactory.issue(assignee))
                .extracting(Issue::getAssignee)
                .isEqualTo(assignee);
    }

    @Test
    void should_generate_issue_with_modifiers() {
        final String key = "MY-KEY";
        final Type type = Type.BUG;
        final Status status = Status.CLOSED;
        final User assignee = userFactory.user();

        final IssueAbstractFactory modifiedFactory = issueFactory
                .key(key)
                .type(type)
                .status(status)
                .assignee(assignee);

        assertThat(modifiedFactory.issue())
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus, Issue::getAssignee)
                .containsExactly(key, type, status, assignee);

        assertThat(modifiedFactory.defaultIssue())
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus, Issue::getAssignee)
                .containsExactly(key, type, status, assignee);
    }

    @Test
    void should_get_default_issue() {
        final Issue issue = issueFactory.defaultIssue();
        assertThat(issue)
                .extracting(Issue::getKey, Issue::getType, Issue::getStatus)
                .containsExactly("DEFAULT", Type.STORY, Status.OPEN);
    }

    @Test
    void should_modify_simple_complex_paths() {
        final User commenter = userFactory.user();

        assertThat(issueFactory.firstCommenter(commenter).issue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);

        assertThat(issueFactory.firstCommenter(commenter).defaultIssue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);
    }

    @Test
    void should_modify_multiple_complex_paths() {
        final User commenter = userFactory.user();

        assertThat(issueFactory.allCommenter(commenter).issue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);

        assertThat(issueFactory.allCommenter(commenter).defaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);
    }

    @Test
    void should_not_fail_on_wrong_complex_paths() {
        final User commenter = userFactory.user();

        assertThat(issueFactory.wrongCommenter(commenter).issue().getComments())
                .extracting(Comment::getCommenter)
                .doesNotContain(commenter);

        assertThat(issueFactory.wrongCommenter(commenter).defaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .doesNotContain(commenter);
    }


}
