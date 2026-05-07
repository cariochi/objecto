package com.cariochi.issuestest.factories;

import com.cariochi.issuestest.model.Attachment;
import com.cariochi.issuestest.model.Comment;
import com.cariochi.issuestest.model.Issue;
import com.cariochi.issuestest.model.Issue.DependencyType;
import com.cariochi.issuestest.model.Issue.Status;
import com.cariochi.issuestest.model.User;
import com.cariochi.objecto.*;
import com.cariochi.objecto.Datafaker.Base.Company;
import com.cariochi.objecto.Datafaker.Base.File;
import com.cariochi.objecto.Datafaker.Base.Lorem;
import com.cariochi.objecto.random.ObjectoRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ImportFactory({UserFactory.class, DatesFactory.class})
@Generate.MaxRecursionDepth(3)
public interface BaseIssueFactory {

    @PrimaryGenerator
    @Reference("subtasks[*].parent")
    @Datafaker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue();

    @Datafaker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue(@Modify("type=?") Issue.Type type);

    @Datafaker(field = "key", expression = "#{numerify 'ID-####'}")
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

    @Provider
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent("").build();
    }

    @FieldGenerator(type = Attachment.class, field = "fileName")
    @Datafaker(expression = File.FILE_NAME)
    String attachmentFileNameGenerator();

    @FieldGenerator(type = Comment.class, field = "commenter")
    private User commenterGenerator(ObjectoRandom random) {
        return User.builder()
                .fullName("Vadym Deineka")
                .companyName(random.strings().datafaker().nextString(Company.NAME))
                .build();
    }

    @FieldGenerator(type = Issue.class, field = "labels")
    private List<String> labelsGenerator(ObjectoRandom random) {
        return List.of("LABEL1", random.strings().datafaker().nextString(Lorem.WORD).toUpperCase());
    }

    @FieldGenerator(type = Issue.class, field = "properties.value")
    private String issuePropertyValue() {
        return "PROP";
    }

    @FieldGenerator(type = Issue.class, field = "properties.setSize(?)")
    private int issuePropertySize() {
        return 101;
    }

    @PostProcess
    private void issueLabelsPostProcessor(Issue issue) {
        final List<String> labels = new ArrayList<>(issue.getLabels());
        labels.add(0, issue.getKey());
        issue.setLabels(labels);
    }

}
