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
    <version>2.0.0</version>
</dependency>
```

# Examples:

## Declaration

```java
import com.cariochi.objecto.DatafakerMethod;
import com.cariochi.objecto.FieldFactory;
import com.cariochi.objecto.Fields;
import com.cariochi.objecto.InstanceFactory;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.References;
import com.cariochi.objecto.Settings;

public interface IssueFactory {

    @References("subtasks[*].parent")
    @Fields.Datafaker(field = "creationDate", method = DatafakerMethod.TimeAndDate.Past)
    Issue createIssue();

    Issue createIssue(@Modifier("type") Type type);

    @InstanceFactory
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @FieldFactory(type = Issue.class, field = "key")
    private String issueKeyGenerator() {
        return "ID-" + new Faker().number().randomNumber(4, true);
    }

    @Modifier("type")
    IssueFactory withType(Type type);

    @Modifier("setStatus(?)")
    IssueFactory withStatus(Status status);

    @Modifier("subtasks[*].status")
    IssueFactory withAllSubtaskStatuses(Status status);

    @Settings.MaxDepth(5)
    @Fields.Datafaker(field = "fullName", method = Datafaker.Name.FullName)
    @Fields.Datafaker(field = "phone", method = Datafaker.PhoneNumber.CellPhone)
    @Fields.Datafaker(field = "companyName", method = Datafaker.Company.Name)
    User createUser();

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

