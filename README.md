# Cariochi Objecto

Objecto is a lightweight Java library designed to simplify and enhance the process of object creation and testing in unit tests.
It provides a convenient way to generate complex objects with various configurations, making it easier for developers to focus on testing their logic rather than creating elaborate object setups.

# Documentation

Please, see the recently published documentation [here](https://www.cariochi.com/objecto).

# Installation

To use **Objecto** in your project, add the following dependency to your build configuration:

```xml
<dependency>
    <groupId>com.cariochi.objecto</groupId>
    <artifactId>objecto</artifactId>
    <version>1.0.4</version>
</dependency>
```

# Examples:

## Declaration

```java
import com.cariochi.objecto.Instantiator;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.Generator;
import com.cariochi.objecto.WithSettings;
import com.cariochi.issuestest.model.issues.Issue;
import com.cariochi.issuestest.model.issues.Issue.Fields;
import com.cariochi.issuestest.model.issues.Issue.Status;
import com.cariochi.issuestest.model.issues.Issue.Type;
import com.cariochi.issuestest.model.issues.User;
import net.datafaker.Faker;

@WithSettings(maxDepth = 5)
public interface IssueFactory {

    @References("subtasks[*].parent")
    Issue createIssue();

    Issue createIssue(@Modifier("type") Type type);

    @Instantiator
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @Generator
    private String stringGenerator() {
        return new Faker().lorem().sentence();
    }

    @Generator(type = Issue.class, expression = Issue.Fields.key)
    private String issueKeyGenerator() {
        return "ID-" + new Faker().number().randomNumber(4, true);
    }

    @Modifier("type")
    IssueFactory withType(Type type);

    @Modifier("status")
    IssueFactory withStatus(Status status);

    @Modifier("subtasks[*].status")
    IssueFactory withAllSubtaskStatuses(Status status);

}
```

## Usage

```java
// Creating a factory for generating Issue objects
IssueFactory issueFactory = Objecto.create(IssueFactory.class);

// Generating a random Issue object
Issue randomIssue = issueFactory.createIssue();

// Generating an Issue object with specific parameters
Issue randomBug = issueFactory.createIssue(Type.BUG);

// Using modifier methods for easy modification
Issue randomOpenBug = issueFactory
                          .withType(Type.BUG)
                          .withStatus(Status.OPEN)
                          .createIssue();
```

