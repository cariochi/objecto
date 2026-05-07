# Objecto

Objecto is a Java testing library for generating complete object graphs with controlled random data.
It is designed for tests that need realistic domain objects without large fixture builders, repetitive
setters, or fragile JSON snapshots.

With Objecto, you describe how your test data should be generated in a factory interface or abstract
class. Objecto creates an implementation at runtime and uses your annotations, custom generator methods,
constructors, modifiers, references, and post-processors to build objects.

# What Objecto Helps With

- Generate POJOs, collections, maps, arrays, optionals, streams, enums, primitives, numbers, strings,
  UUIDs, dates, and nested object graphs.
- Configure ranges, sizes, nullability, recursion depth, string generation, dates, and constant values
  through annotations.
- Use [Datafaker](https://www.datafaker.net/) expressions for realistic names, addresses, company names,
  dates, phone numbers, and other domain-like values.
- Define reusable factories for test models.
- Override generated fields through method parameters or fluent modifier methods.
- Link bidirectional and nested references after generation.
- Make random generation reproducible with seeds.
- Print failing test seeds with the JUnit 5 extension.

# Requirements

- Java 17 or newer.
- Maven-compatible dependency management.
- JUnit 5 if you want to use `ObjectoExtension`.

# Installation

```xml
<dependency>
    <groupId>com.cariochi.objecto</groupId>
    <artifactId>objecto</artifactId>
    <version>2.1.0</version>
    <scope>test</scope>
</dependency>
```

Use test scope unless your application intentionally needs generated objects outside tests.

# Quick Start

Create a factory interface:

```java
import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.Generate;

import static com.cariochi.objecto.Datafaker.Base.Company;
import static com.cariochi.objecto.Datafaker.Base.Name;

interface UserFactory {

    @Generate.Strings.Length(field = "username", value = 12)
    @Datafaker(field = "fullName", expression = Name.FULL_NAME)
    @Datafaker(field = "companyName", expression = Company.NAME)
    User createUser();
}
```

Ask Objecto for an implementation:

```java
UserFactory users = Objecto.create(UserFactory.class);

User user = users.createUser();
```

Objecto will instantiate `User`, generate values for its fields, apply `@Generate` constraints, and use
[Datafaker](https://www.datafaker.net/) for the annotated fields.

# Factory Basics

A factory can be an interface or an abstract class. Factory methods usually return the type you want to
generate:

```java
interface IssueFactory {

    Issue createIssue();

    List<Issue> createIssues();

    Map<String, Issue> createIssueMap();
}
```

Create a factory with:

```java
IssueFactory factory = Objecto.create(IssueFactory.class);
```

You can also create it with a fixed seed:

```java
IssueFactory factory = Objecto.create(IssueFactory.class, 42L);
```

Factory methods may be abstract or implemented. Implemented methods are invoked normally, and Objecto
still applies configured modifiers to their result.

```java
interface IssueFactory {

    default Issue createDefaultIssue() {
        return Issue.builder()
                .key("DEFAULT")
                .status(Status.OPEN)
                .build();
    }
}
```

# Generation Coverage

Objecto generates common Java values out of the box: primitives and boxed types, strings, numbers,
`BigDecimal`, booleans, UUIDs, enums, arrays, collections, maps, optionals, streams, `java.util.Date`,
Java time types, and nested custom objects.

For collection interfaces such as `List`, `Set`, `Queue`, `Collection`, and `Map`, Objecto creates
default concrete implementations. For custom classes, it tries constructors and supported static factory
methods, generating arguments where needed. If a type needs special construction logic, provide it with
`@Provider`.

# Configuration With `@Generate`

`@Generate` annotations can be placed on a factory type or on a factory method.

Type-level configuration applies to methods declared by that type, inherited interfaces, and supertypes.
Method-level configuration applies only to that method and can override or narrow the behavior for a
specific generation call.

```java
@Generate.MaxDepth(5)
@Generate.MaxRecursionDepth(3)
interface IssueFactory {

    @Generate.Collections.Size(field = "comments", value = 3)
    @Generate.Strings.Length.Range(field = "key", from = 8, to = 12)
    @Generate.Integers.Range(field = "priority", from = 1, to = 6)
    Issue createIssue();
}
```

Field-specific annotations use the `field` attribute:

```java
interface IssueFactory {

    @Generate.SetValue(field = "status", value = "OPEN")
    @Generate.SetNull(field = "closedAt")
    @Generate.Nullable(field = "assignee", value = true)
    Issue createIssue();
}
```

For collection-like return values, use `[*]` to configure generated elements:

```java
interface IssueFactory {

    @Generate.SetValue(field = "[*].status", value = "OPEN")
    List<Issue> createOpenIssues();
}
```

## Available `@Generate` Annotations

| Annotation                                                          | Purpose                                                         |
|---------------------------------------------------------------------|-----------------------------------------------------------------|
| `@Generate.MaxDepth(value)`                                         | Maximum object graph depth.                                     |
| `@Generate.MaxRecursionDepth(value)`                                | Maximum recursion depth for the same type.                      |
| `@Generate.Nullable(value)`                                         | Allows generated reference values to be randomly null.          |
| `@Generate.SetNull(field)`                                          | Always sets a field to null.                                    |
| `@Generate.SetValue(field, value)`                                  | Parses and assigns a constant string value.                     |
| `@Generate.Longs.Range(from, to)`                                   | Range for `long` and `Long`.                                    |
| `@Generate.Integers.Range(from, to)`                                | Range for `int` and `Integer`.                                  |
| `@Generate.Shorts.Range(from, to)`                                  | Range for `short` and `Short`.                                  |
| `@Generate.Bytes.Range(from, to)`                                   | Range for `byte` and `Byte`.                                    |
| `@Generate.Chars.Range(from, to)`                                   | Range for `char` and `Character`.                               |
| `@Generate.BigDecimals.Range(from, to)`                             | Range for `BigDecimal`.                                         |
| `@Generate.BigDecimals.Scale(value)`                                | Scale for generated `BigDecimal` values.                        |
| `@Generate.Doubles.Range(from, to)`                                 | Range for `double` and `Double`.                                |
| `@Generate.Floats.Range(from, to)`                                  | Range for `float` and `Float`.                                  |
| `@Generate.Dates.Range(from, to, timezone)`                         | Date/time range. Values are ISO-8601 strings.                   |
| `@Generate.Collections.Size(value)`                                 | Exact collection size.                                          |
| `@Generate.Collections.Size.Range(from, to)`                        | Collection size range.                                          |
| `@Generate.Arrays.Size(value)`                                      | Exact array size.                                               |
| `@Generate.Arrays.Size.Range(from, to)`                             | Array size range.                                               |
| `@Generate.Maps.Size(value)`                                        | Exact map size.                                                 |
| `@Generate.Maps.Size.Range(from, to)`                               | Map size range.                                                 |
| `@Generate.Strings.Length(value)`                                   | Exact string length.                                            |
| `@Generate.Strings.Length.Range(from, to)`                          | String length range.                                            |
| `@Generate.Strings.Characters(chars, from, to, fieldNamePrefix)`    | Character source and optional field-name prefixing for strings. |

For range annotations, `from` is the lower bound and `to` is the upper bound.

# Datafaker With `@Datafaker`

Objecto integrates with [Datafaker](https://www.datafaker.net/). Use `@Datafaker` when a field should contain
realistic data instead of random letters or numbers. Datafaker organizes generated values by
[providers](https://www.datafaker.net/documentation/providers/), and Objecto passes
[Datafaker expressions](https://www.datafaker.net/documentation/expressions/) to those providers.

```java
import static com.cariochi.objecto.Datafaker.Base.Address;
import static com.cariochi.objecto.Datafaker.Base.Name;
import static com.cariochi.objecto.Datafaker.Base.PhoneNumber;

interface UserFactory {

    @Datafaker(field = "fullName", expression = Name.FULL_NAME)
    @Datafaker(field = "phone", expression = PhoneNumber.CELL_PHONE)
    @Datafaker(field = "city", expression = Address.CITY, locale = "fr")
    User createUser();
}
```

You can also apply a default Datafaker expression or locale to the whole generated object:

```java
@Datafaker(expression = Datafaker.Base.Lorem.PARAGRAPH, locale = "fr")
interface TextFactory {

    Article createArticle();
}
```

Custom [Datafaker expressions](https://www.datafaker.net/documentation/expressions/) are supported:

```java
interface IssueFactory {

    @Datafaker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue();
}
```

Objecto exposes many common [Datafaker provider](https://www.datafaker.net/documentation/providers/)
expressions as constants under `com.cariochi.objecto.Datafaker`. For expressions not exposed as constants,
use [Datafaker expression syntax](https://www.datafaker.net/documentation/expressions/) directly.

# Type Generators

Any factory method that returns a type and has no parameters can become a generator for that return type.
When Objecto needs that type inside another generated object, it may call the method.

If several methods return the same type, mark the primary one with `@PrimaryGenerator`:

```java
interface UserFactory {

    @PrimaryGenerator
    @Datafaker(field = "fullName", expression = Datafaker.Base.Name.FULL_NAME)
    User createUser();

    default User createEmptyUser() {
        return User.builder().build();
    }
}
```

Custom generator methods can accept one supported random parameter:

```java
import com.cariochi.objecto.random.ObjectoRandom;

interface UserFactory {

    @PrimaryGenerator
    default User createUser(ObjectoRandom random) {
        return User.builder()
                .username("user-" + random.nextInt(1000, 10_000))
                .build();
    }
}
```

Supported custom generator signatures are:

- no parameters
- one `com.cariochi.objecto.random.ObjectoRandom` parameter
- one `java.util.Random` parameter

The same signature rules apply to `@FieldGenerator` and `@PostProcess` methods.

# Field Generators

Use `@FieldGenerator` for one field of one generated type:

```java
import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.random.ObjectoRandom;

interface IssueFactory {

    @FieldGenerator(type = Issue.class, field = "key")
    default String issueKey(ObjectoRandom random) {
        return "ID-" + random.nextInt(1000, 10_000);
    }
}
```

Field generators can also target nested fields or setter-like method calls:

```java
interface IssueFactory {

    @FieldGenerator(type = Issue.class, field = "properties.value")
    default String propertyValue() {
        return "PROP";
    }

    @FieldGenerator(type = Issue.class, field = "properties.setSize(?)")
    default int propertySize() {
        return 101;
    }
}
```

You can combine `@FieldGenerator` and `@Datafaker`:

```java
interface AttachmentFactory {

    @FieldGenerator(type = Attachment.class, field = "fileName")
    @Datafaker(expression = Datafaker.Base.File.FILE_NAME)
    String fileName();
}
```

# Custom Construction With `@Provider`

Use `@Provider` when Objecto cannot or should not instantiate a type through its constructors or static
factory methods.

```java
interface AttachmentFactory {

    @Provider
    private Attachment<?> newAttachment() {
        return Attachment.builder()
                .fileContent(new byte[0])
                .build();
    }
}
```

The annotated method must return the type it constructs. Private interface methods are supported.

# Reusing Other Factories With `@ImportFactory`

Factories can import generators, providers, references, field generators, and post-processors from other
factories:

```java
@ImportFactory({UserFactory.class, DatesFactory.class})
interface IssueFactory {

    Issue createIssue();
}
```

This is useful when multiple domain factories need the same user, date, ID, or post-processing rules.

# Modifying Generated Objects With `@Modify`

`@Modify` applies values after an object has been generated. It supports direct field assignment, nested
paths, collection wildcards, indexed paths, and method calls.

## Method Parameters

Annotate a factory method parameter to override a value for that call:

```java
interface IssueFactory {

    Issue createIssue(@Modify("type") Issue.Type type);

    List<Issue> createIssues(@Modify("setType(?)") Issue.Type type);
}
```

If the Java parameter name is the same as the field path, `@Modify` can be omitted:

```java
interface IssueFactory {

    Issue createIssue(Issue.Type type);
}
```

Usage:

```java
Issue bug = factory.createIssue(Issue.Type.BUG);

List<Issue> bugs = factory.createIssues(Issue.Type.BUG);
```

When the generated result is a collection or array, Objecto applies the modifier to every element.

## Fluent Modifier Methods

A modifier method can return the factory type. Objecto returns a configured factory copy, so calls can be
chained:

```java
interface IssueFactory {

    @Modify("key")
    IssueFactory withKey(String key);

    @Modify("setStatus(?)")
    IssueFactory withStatus(Issue.Status status);

    @Modify("type")
    IssueFactory withType(Issue.Type type);

    Issue createIssue();
}
```

For simple field names, fluent modifier methods can also omit `@Modify` when the parameter name matches
the target field:

```java
interface IssueFactory {

    IssueFactory withType(Issue.Type type);
}
```

Usage:

```java
Issue issue = factory
        .withKey("AUTH-123")
        .withStatus(Issue.Status.OPEN)
        .createIssue();
```

You can also keep a configured factory and reuse it:

```java
IssueFactory bugsFactory = issueFactory.withType(Issue.Type.BUG);

Issue bug = bugsFactory.createIssue();
```

For direct field assignment, a plain field path such as `type` or `comments[*].commenter` is enough.
Objecto treats it as assignment to that field. Use the `method(?)` form when you want to call a setter or
another method explicitly.

When `@Modify` is omitted, Objecto relies on Java parameter names being available at runtime. If your
build does not retain them, compile with `-parameters` or use `@Modify` explicitly.

## Multiple Arguments

Method-level `@Modify` passes all method arguments into the expression:

```java
interface IssueFactory {

    @Modify("dependencies.put(?, ?)")
    IssueFactory withDependency(Issue.DependencyType type, Issue issue);
}
```

Parameter-level `@Modify` maps each parameter separately:

```java
interface IssueFactory {

    IssueFactory withTypeAndStatus(
            @Modify("setType(?)") Issue.Type type,
            @Modify("setStatus(?)") Issue.Status status
    );
}
```

## Nested Paths And Collections

```java
interface IssueFactory {

    @Modify("comments[*].commenter")
    IssueFactory withAllCommenters(User user);

    @Modify("comments[0].setCommenter(?)")
    IssueFactory withFirstCommenter(User user);

    @Modify("comments[?].commenter")
    IssueFactory withCommenter(int index, User user);
}
```

If Objecto cannot apply a modifier path, it logs the failure at debug level and leaves the object unchanged.

# References With `@Reference`

`@Reference` links generated objects back to the object currently being generated. It is useful for
bidirectional relationships and nested domain graphs.

```java
interface IssueFactory {

    @Reference("subtasks[*].parent")
    Issue createIssue();
}
```

When `Issue` is generated, every generated subtask receives the parent issue in its `parent` field.

Multiple references are supported:

```java
interface CourseFactory {

    @Reference({
            "professor.assignments[*].course",
            "enrollments[*].course"
    })
    Course createCourse();
}
```

Reference paths use the same path style as modifiers.

# Post-Processing With `@PostProcess`

`@PostProcess` methods run after an instance of their first parameter type is generated.

```java
import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.PostProcess;
import com.cariochi.objecto.random.ObjectoRandom;

interface UserFactory {

    @PostProcess
    private void normalizeUser(User user, ObjectoRandom random) {
        String username = user.getFullName()
                .toLowerCase()
                .replace(".", "")
                .replace(" ", ".");

        user.setUsername(username);
        user.setEmail(username + "@" +
                random.strings().datafaker().nextString(Datafaker.Base.Internet.DOMAIN_NAME));
    }
}
```

The method must:

- be annotated with `@PostProcess`
- return `void`
- accept the generated object as the first parameter
- optionally accept `ObjectoRandom` or `java.util.Random` as the second parameter

# Reproducible Randomness With `@Seed`

Use `@Seed` to make random generation deterministic.

```java
@Seed(123)
interface UserFactory {

    User createUser();
}
```

Method-level seed:

```java
interface UserFactory {

    @Seed(320)
    User createUser();
}
```

Programmatic seed:

```java
UserFactory factory = Objecto.create(UserFactory.class, 100L);
```

Seed precedence in normal factory usage:

1. Method-level `@Seed`.
2. Seed passed to `Objecto.create(factoryClass, seed)`.
3. Type-level `@Seed`.
4. Random seed generated by Objecto.

Custom generators that accept `ObjectoRandom` or `java.util.Random` use Objecto-managed randomness and
therefore follow Objecto seeds.

# JUnit 5 Extension

`ObjectoExtension` helps reproduce failing randomized tests. Register it on a test class:

```java
import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.extension.ObjectoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ObjectoExtension.class)
class IssueFactoryTest {

    private final IssueFactory issues = Objecto.create(IssueFactory.class);

    @Test
    void createsIssue() {
        Issue issue = issues.createIssue();

        // assertions
    }
}
```

When a test fails, the extension publishes a JUnit report entry with the seed and test method name:

```text
Objecto Seed = 4676741460335224710
Test Method = createsIssue
```

To reproduce the failure, apply the printed seed to the test method or factory:

```java
@Test
@Seed(4676741460335224710L)
void createsIssue() {
    Issue issue = issues.createIssue();
}
```

The extension also sets that test seed on Objecto factories stored in test instance fields, unless the
factory was already created with an explicit custom seed.

# Complete Example

```java
import com.cariochi.objecto.Datafaker;
import com.cariochi.objecto.FieldGenerator;
import com.cariochi.objecto.Generate;
import com.cariochi.objecto.ImportFactory;
import com.cariochi.objecto.Modify;
import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.PostProcess;
import com.cariochi.objecto.PrimaryGenerator;
import com.cariochi.objecto.Provider;
import com.cariochi.objecto.Reference;
import com.cariochi.objecto.random.ObjectoRandom;

import java.util.ArrayList;
import java.util.List;

@ImportFactory(UserFactory.class)
@Generate.MaxRecursionDepth(3)
interface IssueFactory {

    @PrimaryGenerator
    @Reference("subtasks[*].parent")
    @Datafaker(field = "key", expression = "#{numerify 'ID-####'}")
    Issue createIssue();

    Issue createIssue(@Modify("type") Issue.Type type);

    List<Issue> createIssues(@Modify("setType(?)") Issue.Type type);

    @Modify("key")
    IssueFactory withKey(String key);

    @Modify("setStatus(?)")
    IssueFactory withStatus(Issue.Status status);

    @FieldGenerator(type = Comment.class, field = "commenter")
    private User commenter(ObjectoRandom random) {
        return User.builder()
                .fullName("Test User")
                .companyName(random.strings().datafaker().nextString(Datafaker.Base.Company.NAME))
                .build();
    }

    @FieldGenerator(type = Issue.class, field = "labels")
    private List<String> labels() {
        return List.of("generated");
    }

    @PostProcess
    private void addIssueKeyLabel(Issue issue) {
        List<String> labels = new ArrayList<>(issue.getLabels());
        labels.add(0, issue.getKey());
        issue.setLabels(labels);
    }

    @Provider
    private Attachment<?> attachment() {
        return Attachment.builder()
                .fileContent(new byte[0])
                .build();
    }
}

interface UserFactory {

    @PrimaryGenerator
    @Datafaker(field = "fullName", expression = Datafaker.Base.Name.FULL_NAME)
    @Datafaker(field = "phone", expression = Datafaker.Base.PhoneNumber.CELL_PHONE)
    User createUser();
}

class Example {

    void run() {
        IssueFactory issues = Objecto.create(IssueFactory.class);

        Issue bug = issues
                .withKey("AUTH-100")
                .withStatus(Issue.Status.OPEN)
                .createIssue(Issue.Type.BUG);
    }
}
```

# License

Objecto is distributed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
