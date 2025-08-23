# Introduction

**Objecto** is an open-source Java library for generating random objects and data structures. It streamlines unit testing by letting you declare factories for your domain types and automatically produce randomized instances with flexible constraints.

Key features at a glance:

* **Factory interfaces:** Define interfaces or abstract classes that describe how objects should be created, then call `Objecto.create()` to obtain a factory implementation. This instance provides methods to build random objects and collections.
* **`@Spec` annotation:** Configure global and field-specific rules such as value ranges, collection sizes, nullability and maximum depth via one unified annotation. `@Spec` replaces the previous `@Settings`/`@Fields` annotations and supports nested configuration elements.
* **Datafaker integration:** The `@Faker` annotation binds a field to a [Datafaker](https://www.datafaker.net/) expression (e.g. `Name.FULL_NAME` or `PhoneNumber.CELL_PHONE`) to populate realistic values. You can explore all available expressions in the [Datafaker documentation](https://www.datafaker.net/documentation/providers/).
* **Custom generators:** Use `@DefaultGenerator` to create bespoke instances of a type and `@GenerateField` to supply field-level random generators.
* **Modifiers and references:** The `@Modify` annotation and modifier methods let you set specific values or call methods on generated objects. With `@Reference` you can link entities together, ensuring bidirectional relationships.
* **Seeds and reproducibility:** Annotate a method or factory with `@Seed` to fix the random seed, or configure seeds at the factory instance or test level to reproduce test failures.
* **Post processing:** Apply additional logic after an object is created by marking a method with `@PostGenerate`.

# Getting Started

## Maven Dependency

```xml
<dependency>
    <groupId>com.cariochi.objecto</groupId>
    <artifactId>objecto</artifactId>
    <version>2.0.3</version>
</dependency>
```

## Defining a Factory

A factory is an interface or abstract class that declares methods for creating your domain objects. You annotate these methods to describe how random instances should be generated.

```java
import com.cariochi.objecto.Spec;
import com.cariochi.objecto.Faker;
import com.cariochi.objecto.GenerateField;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.Reference;
import com.cariochi.objecto.DefaultGenerator;
import com.cariochi.objecto.Construct;

public interface IssueFactory {

    // Link subtasks back to their parent issue
    @Reference("subtasks[*].parent")
    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    @Faker(field = "creationDate", expression = Faker.Base.TimeAndDate.PAST)
    Issue createIssue();

    // Allow type to be modified via parameter
    @Faker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue(@Modify("type") Type type);

    // Provide a custom constructor when a public constructor is unavailable
    @Construct
    private Attachment<?> newAttachment() {
        return Attachment.builder().fileContent(new byte[0]).build();
    }

    // Provide a custom generator for a specific field
    @GenerateField(type = Attachment.class, field = "fileName")
    @Faker(expression = Faker.Base.File.FILE_NAME)
    String attachmentFileNameGenerator();

    // Expose fluent modifier methods
    @Modify("type")
    IssueFactory withType(Type type);

    @Modify("setStatus(?)")
    IssueFactory withStatus(Status status);

    // Limit recursion depth and set default Faker expressions when creating users
    @Spec.MaxDepth(5)
    @Faker(field = "fullName", expression = Faker.Base.Name.FULL_NAME)
    @Faker(field = "phone", expression = Faker.Base.PhoneNumber.CELL_PHONE)
    User createUser();
}
```

To obtain a factory implementation, call `Objecto.create()`:

```java
IssueFactory issueFactory = Objecto.create(IssueFactory.class);
Issue randomIssue = issueFactory.createIssue();
```

You can modify objects by supplying parameters to factory methods or by chaining modifier methods:

```java
// Using a method parameter to set the issue type
Issue bug = issueFactory.createIssue(Type.BUG);

// Using modifier methods
Issue openBug = issueFactory
        .withType(Type.BUG)
        .withStatus(Status.OPEN)
        .createIssue();
```

# Annotations and Configuration

## @Spec – Unified Configuration

`@Spec` is a single annotation for declaring constraints on random generation. You can place it on the factory interface or on individual methods. Nested annotations specify ranges, sizes, nullability and more. For field-specific rules, supply the `field` attribute.

Example of global constraints:

```java
@Spec.MaxDepth(5)
@Spec.MaxRecursionDepth(5)
@Spec.Nullable(true)
@Spec.Collections.Size(5)
@Spec.Integers.Range(from = 1, to = 10)
@Spec.Strings.Length(5)
Issue createIssue();
```

Field-specific configuration:

```java
@Spec.Nullable(field = "description", value = true)
@Spec.SetNull(field = "id")
@Spec.SetValue(field = "status", value = "OPEN")
@Spec.Collections.Size(field = "subtasks", value = 5)
Issue createIssue();
```

These constraints replace the older `@Settings` and `@Fields` annotations.

## @Faker – Realistic Data

The `@Faker` annotation binds a field to a [Datafaker](https://www.datafaker.net/) expression. Expressions are grouped into categories such as `Name`, `PhoneNumber`, `Company`, `TimeAndDate` and many more. You can also specify a locale. A full list of available providers and expressions can be found in the [Datafaker documentation](https://www.datafaker.net/documentation/providers/).

```java
@Faker(field = "fullName", expression = Faker.Base.Name.FULL_NAME)
@Faker(field = "phone", expression = Faker.Base.PhoneNumber.CELL_PHONE)
@Faker(field = "companyName", expression = Faker.Base.Company.NAME)
Issue createIssue();
```

Custom expressions can also be specified using the `#{...}` syntax, for example:

```java
@Faker(field = "key", expression = "#{numerify 'ID-####'}")
```

## @DefaultGenerator – Type Generators

Use `@DefaultGenerator` to define a factory method that returns a fully initialized instance of a type. This method is used whenever an instance of that type is required (unless overridden by field generators, modifiers, etc.).

### Abstract method

```java
@DefaultGenerator
@Reference("subtasks[*].parent")
@Faker(field = "key", expression = "#{numerify 'ID-####'}")
Issue createIssue();
```

### Custom implementation

**Parameters & seeding:** supported signatures are no-arg, `com.cariochi.objecto.random.ObjectoRandom`, or `java.util.Random`. See [Random Parameters and Seed Behavior](#random-parameters-and-seed-behavior) in the [@Seed – Reproducible Randomness](#seed--reproducible-randomness) section for details.

```java
@DefaultGenerator
default Issue createIssue(ObjectoRandom random) {
    return Issue.builder()
        .key("ID-" + random.nextInt(1000, 9999))
        .name(random.nextString())
        .build();
}
```

## @GenerateField – Field Generators

`@GenerateField` defines a factory method for a specific field.

It is useful for fine-grained control of how individual fields are generated.

### Abstract method

```java
@GenerateField(type = Attachment.class, field = "fileName")
@Faker(expression = Faker.Base.File.FILE_NAME)
String attachmentFileNameGenerator();
```

### Custom implementation

**Parameters & seeding:** the generator method may be no-arg, accept `com.cariochi.objecto.random.ObjectoRandom`, or `java.util.Random`. See [Random Parameters and Seed Behavior](#random-parameters-and-seed-behavior) in the [@Seed – Reproducible Randomness](#seed--reproducible-randomness) section for details.

```java
@GenerateField(type = Comment.class, field = "commenter")
private User commenterGenerator(ObjectoRandom random) {
    return User.builder()
            .fullName("Vadym Deineka")
            .companyName(random.strings().faker().nextString(Company.NAME))
            .build();
}
```

## @Construct – Custom Constructors

If an object cannot be instantiated via a public constructor or static factory, annotate a method with `@Construct` to supply a custom creation method. This method must return an instance of the type.

```java
@Construct
private Attachment<?> newAttachment() {
    return Attachment.builder().fileContent(new byte[0]).build();
}
```

## @Modify – Modifiers

The `@Modify` annotation is a powerful way to adjust generated objects. It can be applied in different contexts depending on whether you want to influence a single generation or configure a reusable factory.

### 1. As a parameter of a factory method

This approach creates a random object but overrides selected values directly through method parameters.

```java
Issue createIssue(@Modify("type") Type type);
```

**Usage:**

```java
Issue bug = factory.createIssue(Type.BUG);
```

### 2. As a factory interface method

Here, the modifier is declared as part of the factory interface. Such methods must return the factory type itself, enabling fluent configuration chains.

```java
@Modify("type")
IssueFactory withType(Type type);

@Modify("setStatus(?)")
IssueFactory withStatus(Status status);
```

**Usage:**

```java
Issue issue = factory.withType(Type.BUG).withStatus(Status.OPEN).createIssue();
```

### 3. Calling methods on generated objects

`@Modify` can also invoke setter or custom methods during object creation.

```java
@Modify("setAssignee(?)")
IssueFactory withAssignee(User assignee);
```

### 4. Multiple arguments

You can combine several `@Modify` annotations on different parameters of the same method.

```java
IssueFactory withTypeAndStatus(
        @Modify("setType(?)") Type type,
        @Modify("setStatus(?)") Status status
);
```

### 5. Nested and collection modifications

Expressions support deep navigation and collections.

```java
// Modify all subtasks' status
@Modify("subtasks[*].status")
IssueFactory withAllSubtaskStatuses(Status status);

// Modify the commenter of the first comment
@Modify("comments[0].commenter=?")
IssueFactory withFirstCommenter(User commenter);
```

### 6. Fluent chaining

All factory interface methods annotated with `@Modify` return the factory itself. This allows combining multiple modifications before generating the final object.

```java
Issue inProgressBug = factory
        .withType(Type.BUG)
        .withStatus(Status.IN_PROGRESS)
        .withAssignee(new User("qa"))
        .createIssue();
```

## @Reference – Linking Entities

`@Reference` ensures relationships between generated objects are maintained. The value is an expression pointing to the fields that should refer back to the parent.

```java
@Reference("subtasks[*].parent")
Issue createIssue();
```

Multiple references can be specified in one annotation.

## @Seed – Reproducible Randomness

The `@Seed` annotation makes random generation reproducible. You can apply it at different levels:

* **On a factory method** – fixes the random seed for objects created by that method.

  ```java
  @Seed(12345)
  Issue createIssue();
  ```

* **On the factory interface** – sets a global seed for all generated objects in that factory.

  ```java
  @Seed(999)
  public interface IssueFactory {
    Issue createIssue();
  }
  ```

* **Programmatically** – supply a seed when creating a factory:

  ```java
  IssueFactory factory = Objecto.create(IssueFactory.class, 100L);
  ```

### Random Parameters and Seed Behavior

Several annotations support generator methods that can take different parameters:
- **No parameters** — you can use any random inside the body. If you rely on your own random source, Objecto's `@Seed` does not apply to that source.
- **`com.cariochi.objecto.random.ObjectoRandom` parameter** — Objecto-managed random. `@Seed` applies (Objecto controls this RNG).
- **`java.util.Random` parameter** — standard Java RNG provided by Objecto. `@Seed` applies (Objecto seeds the provided `Random`).

This logic is the same for methods annotated with `@DefaultGenerator`, `@GenerateField`, and `@PostGenerate`.

### Using JUnit 5 Extension

When you run tests with JUnit 5, you can register the `ObjectoExtension`. The extension has a simple purpose: **if a test fails, it automatically prints the current random seed to the console**.

This allows you to copy that seed and rerun the same test with it to reproduce the failure and debug it deterministically.

The console output is provided via JUnit report entries. A typical log line looks like (this is a JUnit report entry that appears in the test logs):

```text
2025-08-23T12:04:22.349083  Objecto Seed = 4676741460335224710  Test Method = testCreateUser
```

This is implemented in the extension’s `afterEach` hook using `context.publishReportEntry(...)`.

```java
@ExtendWith(ObjectoExtension.class)
class IssueFactoryTest {

    @Test
    void shouldCreateDeterministicIssue(IssueFactory factory) {
        Issue issue = factory.createIssue();
        // assertions
    }
}
```

In this example, if the test fails, the console will show the seed used. You can then rerun the test with that seed by applying `@Seed` on the factory or method, or by passing the seed programmatically to `Objecto.create(...)`. Seeds specified on methods override seeds defined on the factory.

## @PostGenerate – Post Processing

Methods annotated with `@PostGenerate` run after an object is fully generated and can perform additional transformations.

**Parameters & seeding:** post-processing methods can be no-arg or accept `com.cariochi.objecto.random.ObjectoRandom` or `java.util.Random`. See [Random Parameters and Seed Behavior](#random-parameters-and-seed-behavior) in the [@Seed – Reproducible Randomness](#seed--reproducible-randomness) section for details.

```java
@PostGenerate
private void userPostProcessor(User user, ObjectoRandom random) {
    String username = replace(replace(lowerCase(user.getFullName()), ".", ""), " ", ".");
    user.setUsername(username);
    user.setEmail(username + "@" + random.strings().faker().nextString(Internet.DOMAIN_NAME));
}
```

# Quick Recipes

## Create a factory and generate an object

```java
IssueFactory factory = Objecto.create(IssueFactory.class);
Issue issue = factory.createIssue();
```

## Generate with specific values

```java
Issue bug = factory.createIssue(Type.BUG);

Issue inProgressBug = factory
        .withType(Type.BUG)
        .withStatus(Status.IN_PROGRESS)
        .createIssue();
```

## Set constraints with @Spec

```java
@Spec.Integers.Range(from = 1, to = 10)
@Spec.Collections.Size(field = "subtasks", value = 3)
Issue createIssue();
```

## Use Datafaker via @Faker

```java
@Faker(field = "fullName", expression = Faker.Base.Name.FULL_NAME)
@Faker(field = "creationDate", expression = Faker.Base.TimeAndDate.PAST)
Issue createIssue();
```

## Provide custom generators

```java
@DefaultGenerator
default User createUser(ObjectoRandom random) {
return User.builder()
        .fullName(random.strings().faker().nextString(Faker.Base.Name.FULL_NAME))
        .build();
}

@GenerateField(type = Issue.class, field = "key")
String generateKey(ObjectoRandom random) {
    return "ID-" + random.nextInt(1000, 10000);
}
```

## Link related entities

```java
@Reference("subtasks[*].parent")
Issue createIssue();
```

## Control randomness

```java
@Seed(42)
Issue createIssue();

IssueFactory factory = Objecto.create(IssueFactory.class, 42L);
```

## Post-process objects

```java
@PostGenerate
private void normalize(User user) {
    user.setUsername(user.getFullName().toLowerCase().replace(" ", "."));
}
```

# License

Objecto is distributed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

