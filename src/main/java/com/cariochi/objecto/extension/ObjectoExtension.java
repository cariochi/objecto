package com.cariochi.objecto.extension;

import com.cariochi.objecto.Seed;
import com.cariochi.objecto.proxy.HasSeed;
import com.cariochi.objecto.utils.ObjectoRandom;
import com.cariochi.reflecto.fields.TargetField;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.cariochi.reflecto.Reflecto.reflect;

@Slf4j
public class ObjectoExtension implements BeforeEachCallback, AfterEachCallback {

    private long seed;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {

        seed = reflect(extensionContext.getRequiredTestMethod()).annotations().find(Seed.class)
                .map(Seed::value)
                .orElseGet(ObjectoRandom::randomSeed);

        reflect(extensionContext.getRequiredTestInstance()).fields().stream()
                .map(TargetField::getValue)
                .filter(HasSeed.class::isInstance)
                .map(HasSeed.class::cast)
                .filter(hasSeed -> !hasSeed.isCustomSeed())
                .forEach(hasSeed -> hasSeed.setSeed(seed));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (extensionContext.getExecutionException().isPresent()) {
            log.info("Test method '{}' failed with seed: {}", extensionContext.getRequiredTestMethod().getName(), seed);
        }
    }

}
