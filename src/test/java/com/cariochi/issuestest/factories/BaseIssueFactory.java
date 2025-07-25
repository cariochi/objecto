package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.Attachment;
import com.cariochi.issuestest.model.Comment;
import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.Construct;
import com.cariochi.objecto.DefaultGenerator;
import com.cariochi.objecto.Faker;
import com.cariochi.objecto.Faker.Base.Company;
import com.cariochi.objecto.Faker.Base.File;
import com.cariochi.objecto.Faker.Base.Lorem;
import com.cariochi.objecto.GenerateField;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.PostGenerate;
import com.cariochi.objecto.Reference;
import com.cariochi.objecto.Spec;
import com.cariochi.objecto.UseFactory;
import com.cariochi.objecto.random.ObjectoRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@UseFactory({UserFactory.class, DatesFactory.class})
@Spec.MaxRecursionDepth(3)
public interface BaseIssueFactory {

    @DefaultGenerator
    @Reference("subtasks[*].parent")
    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue();

    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue(@Modify("type=?") Issue.Type type);

    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue(@Modify("assignee=?") User assignee);

    List<Issue> createIssues();

    List<Issue> createIssues(@Modify("setType(?)") Issue.Type type);

    @Modify("dependencies.put(?, ?)")
    Issue createIssuesWithDependency(DependencyType type, Issue issue);

    default Issue createDefaultIssue() {
        return Issue.builder()
                .key("DEFAULT")
                .type(Issue.Type.STORY)
                .status(Status.OPEN)
                .comments(List.of(Comment.builder().build()))
                .dependencies(new HashMap<>())
                .build();
    }

    @Construct
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent("").build();
    }

    @GenerateField(type = Attachment.class, field = "fileName")
    @Faker(expression = File.FILE_NAME)
    String attachmentFileNameGenerator();

    @GenerateField(type = Comment.class, field = "commenter")
    private User commenterGenerator(ObjectoRandom random) {
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(random.strings().faker().nextString(Company.NAME))
                .build();
    }

    @GenerateField(type = Issue.class, field = "labels")
    private List<String> labelsGenerator(ObjectoRandom random) {
        return List.of("LABEL1", random.strings().faker().nextString(Lorem.WORD).toUpperCase());
    }

    @GenerateField(type = Issue.class, field = "properties.value")
    private String issuePropertyValue() {
        return "PROP";
    }

    @GenerateField(type = Issue.class, field = "properties.setSize(?)")
    private int issuePropertySize() {
        return 101;
    }

    @PostGenerate
    private void issueLabelsPostProcessor(Issue issue) {
        final List<String> labels = new ArrayList<>(issue.getLabels());
        labels.add(0, issue.getKey());
        issue.setLabels(labels);
    }

}
