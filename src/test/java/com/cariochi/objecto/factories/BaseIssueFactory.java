package com.cariochi.objecto.factories;

import com.cariochi.objecto.Generator;
import com.cariochi.objecto.Instantiator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.PostProcessor;
import com.cariochi.objecto.References;
import com.cariochi.objecto.issues.model.Attachment;
import com.cariochi.objecto.issues.model.Comment;
import com.cariochi.objecto.issues.model.Issue;
import com.cariochi.objecto.issues.model.Issue.DependencyType;
import com.cariochi.objecto.issues.model.Issue.Fields;
import com.cariochi.objecto.issues.model.Issue.Status;
import com.cariochi.objecto.issues.model.Issue.Type;
import com.cariochi.objecto.issues.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.datafaker.Faker;

public interface BaseIssueFactory extends BaseFactory, BaseUserGenerators {

    @References({"subtasks[*].parent", "parent.subtasks[*]"})
    Issue createIssue();

    Issue createIssue(@Modifier("type=?") Type type);

    Issue createIssue(@Modifier("assignee=?") User assignee);

    List<Issue> createIssues();

    List<Issue> createIssues(@Modifier("setType(?)") Type type);

    @Modifier("dependencies.put(?, ?)")
    Issue createIssuesWithDependency(DependencyType type, Issue issue);

    default Issue createDefaultIssue() {
        return Issue.builder()
                .key("DEFAULT")
                .type(Type.STORY)
                .status(Status.OPEN)
                .comments(List.of(Comment.builder().build()))
                .dependencies(new HashMap<>())
                .build();
    }

    @Instantiator
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @Generator(type = Issue.class, expression = Issue.Fields.key)
    private String issueKeyGenerator() {
        return "ID-" + new Faker().number().randomNumber(4, true);
    }

    @Generator(type = Comment.class, expression = Comment.Fields.commenter)
    private User commenterGenerator() {
        Faker faker = new Faker();
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(faker.company().name())
                .build();
    }

    @Generator(type = Issue.class, expression = Fields.labels)
    private List<String> labelsGenerator() {
        return List.of("LABEL1", new Faker().lorem().word().toUpperCase());
    }

    @Generator(type = Issue.class, expression = "properties.value")
    private String issuePropertyValue() {
        return "PROP";
    }

    @Generator(type = Issue.class, expression = "properties.setSize(?)")
    private int issuePropertySize() {
        return 101;
    }

    @PostProcessor
    private void issueLabelsPostProcessor(Issue issue) {
        final List<String> labels = new ArrayList<>(issue.getLabels());
        labels.add(0, issue.getKey());
        issue.setLabels(labels);
    }

}
