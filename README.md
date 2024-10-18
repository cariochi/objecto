# Cariochi Objecto

Objecto is a lightweight Java library designed to simplify and enhance the process of object creation and testing in unit tests. It provides a convenient way to generate
complex objects with various configurations, making it easier for developers to focus on testing their logic rather than creating elaborate object setups.

## Documentation

Please, see the recently published documentation [here](https://www.cariochi.com/objecto).

## Installation

To use **Objecto** in your project, add the following dependency to your build configuration:

```xml

<dependency>
    <groupId>com.cariochi.objecto</groupId>
    <artifactId>objecto</artifactId>
    <version>2.0.1</version>
</dependency>
```

## Examples

### Declaration

```java
import com.cariochi.objecto.Constructor;
import com.cariochi.objecto.DatafakerMethod;
import com.cariochi.objecto.FieldFactory;
import com.cariochi.objecto.Fields;
import com.cariochi.objecto.Modifier;
import com.cariochi.objecto.References;
import com.cariochi.objecto.Settings;

public interface IssueFactory {

    @References("subtasks[*].parent")
    @Fields.Datafaker(field = "creationDate", method = DatafakerMethod.TimeAndDate.Past)
    Issue createIssue();

    Issue createIssue(@Modifier("type") Type type);

    @Constructor
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

### Usage

```java
// Creating a factory for generating Issue objects
IssueFactory issueFactory = Objecto.create(IssueFactory.class);

// Generating a random Issue object
Issue randomIssue = issueFactory.createIssue();

// Using modifier parameter
Issue randomBug = issueFactory.createIssue(Type.BUG);

// Using modifier methods for easy modification
Issue randomOpenBug = issueFactory
        .withType(Type.BUG)
        .withStatus(Status.OPEN)
        .createIssue();
```

## Annotations

### Object Generation Steps

| Step            | Description                                                          | Annotations                                                                      |
|-----------------|----------------------------------------------------------------------|----------------------------------------------------------------------------------|
| Instantiation   | Creating an instance of the object.                                  | `@Constructor`                                                                   |
| Randomization   | Randomly generating values for the object's fields.                  | `@Seed`, `@Settings`, `@Fields`,  `@TypeFactory`, `@FieldFactory`, `@References` |
| Modification    | Setting values using methods and parameters marked with `@Modifier`. | `@Modifier`                                                                      |
| Post-Processing | Processing the object after it has been created.                     | `@PostProcessor`                                                                 |

### @Constructor

Defines a method to instantiate a specific type.

```java
import com.cariochi.objecto.Constructor;

@Constructor
private Attachment<?> newAttachment() {
    return Attachment.builder().fileContent(new byte[0]).build();
}
```

### @Seed

Sets a seed for random data generation to ensure reproducibility.

```java
import com.cariochi.objecto.Seed;

@Seed(12345)
Issue createIssue();
```

### @Settings

Configures various settings for object creation.

```java
import com.cariochi.objecto.Settings;
import com.cariochi.objecto.DatafakerMethod;

@Settings.MaxDepth(5)
@Settings.MaxRecursionDepth(5)
@Settings.Nullable(true)
@Settings.Integers.Range(from = 1, to = 10)
@Settings.Longs.Range(from = 1L, to = 10L)
@Settings.Shorts.Range(from = 1, to = 10)
@Settings.Bytes.Range(from = 1, to = 10)
@Settings.Chars.Range(from = 'a', to = 'z')
@Settings.BigDecimals.Range(from = 1.0, to = 10.0)
@Settings.BigDecimals.Scale(2)
@Settings.Doubles.Range(from = 1.0, to = 10.0)
@Settings.Floats.Range(from = 1.0, to = 10.0)
@Settings.Dates.Range(from = "2024-01-01T00:00:00-05:00", to = "2024-01-02T00:00:00-05:00")
@Settings.Collections.Size(5)
@Settings.Collections.Size.Range(from = 1, to = 10)
@Settings.Arrays.Size(5)
@Settings.Arrays.Size.Range(from = 1, to = 10)
@Settings.Maps.Size(5)
@Settings.Maps.Size.Range(from = 1, to = 10)
@Settings.Strings.Length(5)
@Settings.Strings.Length.Range(from = 1, to = 10)
@Settings.Strings.Parameters(letters = true, digits = true, uppercase = false, useFieldNamePrefix = false)
@Settings.Datafaker.Locale("en")
@Settings.Datafaker.Method(DatafakerMethod.TimeAndDate.Past)
interface IssueFactory {
    Issue createIssue();
}
```

### @Fields

Specifies field-level configurations.

```java
import com.cariochi.objecto.Fields;

@Fields.Nullable(field = "description", value = true)
@Fields.SetNull("id")
@Fields.SetValue(field = "status", value = "OPEN")
@Fields.Size(field = "subtasks", value = 5)
@Fields.Range(field = "priority", from = 1, to = 10)
@Fields.Datafaker(field = "creationDate", method = DatafakerMethod.TimeAndDate.Past)
Issue createIssue();
```

### @TypeFactory

Defines a method to create randomized instances of a specific type, which will be used whenever an instance of the specified type needs to be generated.

```java
import com.cariochi.objecto.Fields;
import com.cariochi.objecto.ObjectoRandom;
import com.cariochi.objecto.References;
import com.cariochi.objecto.TypeFactory;

// custom factory implementation

@TypeFactory
default Issue createIssue(ObjectoRandom random) {
    return Issue.builder()
            .key("ID-" + random.nextInt(1000, 9999))
            .name(random.nextString())
            .build();
}

// or abstract factory
@TypeFactory
@References("subtasks[*].parent")
@Fields.Datafaker(field = "creationDate", method = DatafakerMethod.TimeAndDate.Past)
Issue createIssue();
```

### @FieldFactory

Defines a method to generate values for a specific field.

```java

import com.cariochi.objecto.FieldFactory;

Issue createIssue();

@FieldFactory(type = Issue.class, field = "key")
private String issueKeyGenerator(ObjectoRandom random) {
    return "ID-" + random.nextInt(1000, 9999);
}
```

### @References

Specifies references between objects, useful for setting up relationships.

```java
import com.cariochi.objecto.References;

@References("subtasks[*].parent")
    // sets the parent of each subtask to the generated issue 
Issue createIssue();
```

### @Modifier

Specifies methods to modify the state of the factory or the objects it creates.

```java
import com.cariochi.objecto.Modifier;

Issue createIssue();

@Modifier("type")
IssueFactory withType(Type type);

@Modifier("setStatus(?)")
IssueFactory withStatus(Status status);
```

### @PostProcessor

Defines methods to process objects after they are created.

```java
import com.cariochi.objecto.PostProcessor;

User createUser();

@PostProcessor
default void processUser(User user) {
    final String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
    user.setUsername(username);
    user.setEmail(username + "@" + new Faker().internet().domainName());
}
```


