package com.cariochi.objecto.extension;

import com.cariochi.objecto.Seed;
import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.random.ObjectoRandom;
import com.cariochi.reflecto.fields.TargetField;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Map;

import static com.cariochi.reflecto.Reflecto.reflect;

/**
 * JUnit 5 extension that makes Objecto-based randomized tests easier to reproduce.
 * <p>
 * Before each test, the extension chooses a seed from the test method's {@link Seed} annotation or
 * generates a new random seed. It applies that seed to Objecto factories stored in test instance fields,
 * unless those factories already have an explicit custom seed.
 *
 * <pre>{@code
 * @ExtendWith(ObjectoExtension.class)
 * class IssueFactoryTest {
 *
 *     private final IssueFactory issues = Objecto.create(IssueFactory.class);
 *
 *     @Test
 *     void createsIssue() {
 *         Issue issue = issues.createIssue();
 *     }
 * }
 * }</pre>
 *
 * <p>
 * If a test fails, the extension publishes a JUnit report entry containing the seed and test method name.
 */
@Slf4j
public class ObjectoExtension implements BeforeEachCallback, AfterEachCallback {

    private long seed;

    @Override
    public void beforeEach(ExtensionContext context) {

        seed = reflect(context.getRequiredTestMethod()).annotations().find(Seed.class)
                .map(Seed::value)
                .orElseGet(ObjectoRandom::randomSeed);

        reflect(context.getRequiredTestInstance()).fields().stream()
                .map(TargetField::getValue)
                .filter(HasSeed.class::isInstance)
                .map(HasSeed.class::cast)
                .filter(hasSeed -> !hasSeed.isCustomSeed())
                .forEach(hasSeed -> hasSeed.setSeed(seed));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            context.publishReportEntry(Map.of(
                    "Test Method", context.getRequiredTestMethod().getName(),
                    "Objecto Seed", String.valueOf(seed)
            ));
        }
    }

}
