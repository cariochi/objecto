package com.cariochi.objecto.factories;

import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Param;
import com.cariochi.objecto.TypeConstructor;
import com.cariochi.objecto.model.Attachment;
import com.cariochi.objecto.model.Comment;
import com.cariochi.objecto.model.Issue;
import com.cariochi.objecto.model.Issue.Status;
import com.cariochi.objecto.model.Issue.Type;
import com.cariochi.objecto.model.User;
import java.util.List;
import net.datafaker.Faker;

public interface BaseIssueFactory extends BaseFactory {

    Issue issue();

    Issue issue(@Param("type") Type type);

    Issue issue(@Param("assignee") User assignee);

    default Issue defaultIssue() {
        return Issue.builder()
                .key("DEFAULT")
                .type(Type.STORY)
                .status(Status.OPEN)
                .comments(List.of(Comment.builder().build()))
                .build();
    }

    BaseIssueFactory allCommenter(@Param("comments[*].commenter") User commenter);

    BaseIssueFactory firstCommenter(@Param("comments[0].commenter") User commenter);

    BaseIssueFactory wrongCommenter(@Param("comments[100].commenter") User commenter);

    @TypeConstructor
    default Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @FieldGenerator(type = Issue.class, field = Issue.Fields.key)
    default String issueKey() {
        return "ID-" + new Faker().number().randomNumber(4, true);
    }

    @FieldGenerator(type = Comment.class, field = Comment.Fields.commenter)
    default User commenter() {
        Faker faker = new Faker();
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(faker.company().name())
                .build();
    }

}
