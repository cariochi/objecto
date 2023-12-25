package com.cariochi.objecto;


import com.cariochi.objecto.factories.IssueAbstractFactory;
import com.cariochi.objecto.factories.UserFactory;
import com.cariochi.objecto.model.Attachment;
import com.cariochi.objecto.model.Comment;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import com.cariochi.objecto.utils.Range;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

import static com.cariochi.objecto.ObjectoSettings.Strings.Type.ALPHABETIC;
import static com.cariochi.objecto.ObjectoSettings.Strings.defaultStrings;
import static com.cariochi.objecto.ObjectoSettings.defaultSettings;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractClassFactoryTest {

    private final ObjectoSettings settings = defaultSettings()
            .withDepth(4)
            .withLongs(Range.of(1L, 100_000L))
            .withIntegers(Range.of(1, 100_000))
            .withBytes(Range.of(65, 91))
            .withDoubles(Range.of(1D, 100_000D))
            .withFloats(Range.of(1F, 100_000F))
            .withCollections(Range.of(2, 5))
            .withArrays(Range.of(2, 5))
            .withMaps(Range.of(2, 5))
            .withStrings(defaultStrings().withType(ALPHABETIC).withSize(Range.of(8, 16)).withUppercase(true));

    private final IssueAbstractFactory issueFactory = Objecto.create(IssueAbstractFactory.class);
    private final UserFactory userFactory = Objecto.create(UserFactory.class, settings);

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
        final Status status = Status.CLOSED;
        final User assignee = userFactory.createUser();

        final IssueAbstractFactory modifiedFactory = issueFactory
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

        assertThat(issueFactory.withFirstCommenter(commenter).createIssue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);

        assertThat(issueFactory.withFirstCommenter(commenter).createDefaultIssue().getComments().get(0))
                .extracting(Comment::getCommenter)
                .isEqualTo(commenter);
    }

    @Test
    void should_modify_multiple_complex_paths() {
        final User commenter = userFactory.createUser();

        assertThat(issueFactory.withAllCommenter(commenter).createIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);

        assertThat(issueFactory.withAllCommenter(commenter).createDefaultIssue().getComments())
                .extracting(Comment::getCommenter)
                .containsOnly(commenter);
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


}
