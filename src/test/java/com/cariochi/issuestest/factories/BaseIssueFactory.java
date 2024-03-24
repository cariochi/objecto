package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.Attachment;
import com.cariochi.issuestest.model.Comment;
import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Fields;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.Issue.Type;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Generator;
import com.cariochi.objecto.Instantiator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.PostProcessor;
import com.cariochi.objecto.References;
import com.cariochi.objecto.WithSettings;
import com.cariochi.objecto.settings.Range;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.datafaker.Faker;

@WithSettings(maxRecursionDepth = 3)
public interface BaseIssueFactory extends BaseFactory, BaseUserGenerators {

    @References("subtasks[*].parent")
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
        return Attachment.builder().fileContent("").build();
    }

    @Generator(type = Issue.class, expression = Issue.Fields.key)
    private String issueKeyGenerator(ObjectoRandom random) {
        return "ID-" + random.nextInt(Range.of(1000, 10000));
    }

    @Generator(type = Comment.class, expression = Comment.Fields.commenter)
    private User commenterGenerator(Random random) {
        Faker faker = new Faker(random);
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
