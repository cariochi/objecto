package com.cariochi.objecto.factories;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.InstanceCreator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.model.Attachment;
import com.cariochi.objecto.model.Comment;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public interface BaseIssueFactory extends BaseFactory {

    Issue createIssue();

    Issue createIssue(@Modifier("type") Type type);

    Issue createIssue(@Modifier("assignee") User assignee);

    List<Issue> createIssues();

    List<Issue> createIssues(@Modifier("type") Type type);

    default Issue createDefaultIssue() {
        return Issue.builder()
                .key("DEFAULT")
                .type(Type.STORY)
                .status(Status.OPEN)
                .comments(List.of(Comment.builder().build()))
                .build();
    }

    @Modifier("comments[*].commenter")
    BaseIssueFactory withAllCommenter(User commenter);

    @Modifier("comments[0].commenter")
    BaseIssueFactory withFirstCommenter(User commenter);

    @Modifier("comments[100].commenter")
    BaseIssueFactory withWrongCommenter(User commenter);

    @InstanceCreator
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @FieldGenerator(type = Issue.class, field = Issue.Fields.key)
    private String issueKeyGenerator() {
        return "ID-" + new Faker().number().randomNumber(4, true);
    }

    @FieldGenerator(type = Comment.class, field = Comment.Fields.commenter)
    private User commenterGenerator() {
        Faker faker = new Faker();
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(faker.company().name())
                .build();
    }

}
