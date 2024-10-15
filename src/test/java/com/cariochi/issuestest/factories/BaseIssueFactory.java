package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.Attachment;
import com.cariochi.issuestest.model.Comment;
import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.Issue.Type;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.DatafakerMethod;
import com.cariochi.objecto.FieldFactory;
import com.cariochi.objecto.InstanceFactory;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.PostProcessor;
import com.cariochi.objecto.References;
import com.cariochi.objecto.Settings;
import com.cariochi.objecto.TypeFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import net.datafaker.Faker;

@Settings.MaxRecursionDepth(3)
public interface BaseIssueFactory extends BaseFactory, BaseUserGenerators {

    @TypeFactory
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

    @InstanceFactory
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent("").build();
    }

    @FieldFactory(type = Attachment.class, field = "fileName")
    @Settings.Datafaker.Method(DatafakerMethod.File.FileName)
    String attachmentFileNameGenerator();

    @FieldFactory(type = Issue.class, field = "key")
    private String issueKeyGenerator(ObjectoRandom random) {
        return "ID-" + random.nextInt(1000, 10000);
    }

    @FieldFactory(type = Comment.class, field = "commenter")
    private User commenterGenerator(Random random) {
        Faker faker = new Faker(random);
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(faker.company().name())
                .build();
    }

    @FieldFactory(type = Issue.class, field = "labels")
    private List<String> labelsGenerator(Random random) {
        return List.of("LABEL1", new Faker(random).lorem().word().toUpperCase());
    }

    @FieldFactory(type = Issue.class, field = "properties.value")
    private String issuePropertyValue() {
        return "PROP";
    }

    @FieldFactory(type = Issue.class, field = "properties.setSize(?)")
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
