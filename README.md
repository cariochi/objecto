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
    <version>2.0.3</version>
</dependency>
```

## Examples

### Declaration

```java
import com.cariochi.objecto.Spec;
import com.cariochi.objecto.Faker;
import com.cariochi.objecto.Construct;
import com.cariochi.objecto.Faker.Base.Company;
com.cariochi.objecto.Faker.Base.File;
import com.cariochi.objecto.Faker.Base.Name;
import com.cariochi.objecto.Faker.Base.PhoneNumber;
import com.cariochi.objecto.Faker.Base.TimeAndDate;
import com.cariochi.objecto.GenerateField;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.Reference;

public interface IssueFactory {

    @Reference("subtasks[*].parent")
    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    @Faker(field = "creationDate", expression = TimeAndDate.PAST)
    Issue createIssue();

    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue(@Modify("type") Type type);

    @Construct
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    @GenerateField(type = Attachment.class, field = "fileName")
    @Faker(expression = File.FILE_NAME)
    String attachmentFileNameGenerator();

    @Modify("type")
    IssueFactory withType(Type type);

    @Modify("setStatus(?)")
    IssueFactory withStatus(Status status);

    @Modify("subtasks[*].status")
    IssueFactory withAllSubtaskStatuses(Status status);

    @Spec.MaxDepth(5)
    @Faker(field = "fullName", expression = Name.FULL_NAME)
    @Faker(field = "phone", expression = PhoneNumber.CELL_PHONE)
    @Faker(field = "companyName", expression = Company.NAME)
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

| Step            | Description                                                        | Annotations                                                                             |
|-----------------|--------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| Instantiation   | Creating an instance of the object.                                | `@Construct`                                                                            |
| Randomization   | Randomly generating values for the object's fields.                | `@Seed`, `@Config`,  `@PrimaryGenerator`, `@FieldGenerator`, `@Reference`, `@Datafaker` |
| Modification    | Setting values using methods and parameters marked with `@Modify`. | `@Modify`                                                                               |
| Post-Processing | Processing the object after it has been created.                   | `@PostProcess`                                                                          |

### @Constructor

Defines a method to instantiate a specific type.

```java
import com.cariochi.objecto.Construct;
import com.cariochi.objecto.Constructor;

@Construct
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

### @Config

Configures various settings for object creation.

```java
import com.cariochi.objecto.Spec;

@Spec.MaxDepth(5)
@Spec.MaxRecursionDepth(5)
@Spec.Nullable(true)
@Spec.Integers.Range(from = 1, to = 10)
@Spec.Longs.Range(from = 1L, to = 10L)
@Spec.Shorts.Range(from = 1, to = 10)
@Spec.Bytes.Range(from = 1, to = 10)
@Spec.Chars.Range(from = 'a', to = 'z')
@Spec.BigDecimals.Range(from = 1.0, to = 10.0)
@Spec.BigDecimals.Scale(2)
@Spec.Doubles.Range(from = 1.0, to = 10.0)
@Spec.Floats.Range(from = 1.0, to = 10.0)
@Spec.Dates.Range(from = "2024-01-01T00:00:00-05:00", to = "2024-01-02T00:00:00-05:00")
@Spec.Collections.Size(5)
@Spec.Collections.Size.Range(from = 1, to = 10)
@Spec.Arrays.Size(5)
@Spec.Arrays.Size.Range(from = 1, to = 10)
@Spec.Maps.Size(5)
@Spec.Maps.Size.Range(from = 1, to = 10)
@Spec.Strings.Length(5)
@Spec.Strings.Length.Range(from = 1, to = 10)
@Spec.Strings.Parameters(letters = true, digits = true, uppercase = false, useFieldNamePrefix = false)
interface IssueFactory {
    Issue createIssue();
}
```

### @Fields

Specifies field-level configurations.

```java
import com.cariochi.objecto.Spec;

@Spec.Nullable(field = "description", value = true)
@Spec.SetNull(field = "id")
@Spec.SetValue(field = "status", value = "OPEN")
@Spec.Collections.Size(field = "subtasks", value = 5)
@Spec.Integers.Range(field = "priority", from = 1, to = 10)
Issue createIssue();
```

### @PrimaryGenerator

Defines a method to create randomized instances of a specific type, which will be used whenever an instance of the specified type needs to be generated.

```java

import com.cariochi.objecto.DefaultGenerator;
import com.cariochi.objecto.PrimaryGenerator;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.objecto.Reference;

// custom factory implementation

@DefaultGenerator
default Issue createIssue(ObjectoRandom random) {
    return Issue.builder()
            .key("ID-" + random.nextInt(1000, 9999))
            .name(random.nextString())
            .build();
}

// or abstract factory
@DefaultGenerator
@Reference("subtasks[*].parent")
Issue createIssue();
```

### @FieldGenerator

Defines a method to generate values for a specific field.

```java

import com.cariochi.objecto.GenerateField;
import com.cariochi.objecto.GenerateField;

Issue createIssue();

@GenerateField(type = Issue.class, field = "key")
private String issueKeyGenerator(ObjectoRandom random) {
    return "ID-" + random.nextInt(1000, 9999);
}
```

### @Reference

Specifies references between objects, useful for setting up relationships.

```java
import com.cariochi.objecto.Reference;

@Reference("subtasks[*].parent")
    // sets the parent of each subtask to the generated issue 
Issue createIssue();
```

### @Datafaker

Specifies data generation methods for various fields.

```java
import com.cariochi.objecto.Faker;
import com.cariochi.objecto.Faker.Base.Company;
import com.cariochi.objecto.Faker.Base.Name;
import com.cariochi.objecto.Faker.Base.PhoneNumber;
import com.cariochi.objecto.Faker.Base.TimeAndDate;

@Faker(field = "fullName", expression = Name.FULL_NAME)
@Faker(field = "phone", expression = PhoneNumber.CELL_PHONE)
@Faker(field = "companyName", expression = Company.NAME)
@Faker(field = "creationDate", expression = TimeAndDate.PAST)
Issue createIssue();
```

### @Modify

Specifies methods to modify the state of the factory or the objects it creates.

```java
import com.cariochi.objecto.Modify;

Issue createIssue();

@Modify("type")
IssueFactory withType(Type type);

@Modify("setStatus(?)")
IssueFactory withStatus(Status status);
```

### @PostProcess

Defines methods to process objects after they are generated.

```java
import com.cariochi.objecto.PostGenerate;
import com.cariochi.objecto.PostProcess;

User createUser();

@PostGenerate
default void processUser(User user) {
    final String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
    user.setUsername(username);
    user.setEmail(username + "@" + new Faker().internet().domainName());
}
```


