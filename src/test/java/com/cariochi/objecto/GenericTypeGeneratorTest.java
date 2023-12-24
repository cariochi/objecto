package com.cariochi.objecto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenericTypeGeneratorTest {

    private final DtoFactory factory = Objecto.create(DtoFactory.class);

    @Test
    void should_generate_dto() {
        final Dto<Integer, List<String>> dto = factory.dto();

        final ObjectAssert<Dto<Integer, List<String>>> assertion = assertThat(dto).isNotNull();

        assertion
                .extracting(Dto::getObjectI, Dto::getObjectS, Dto::getParentObjectD, Dto::getParentObjectS, Dto::getParentObjectC)
                .doesNotContainNull();

        assertion
                .extracting(Dto::getArrayI, Dto::getArrayS, Dto::getParentArrayD, Dto::getParentArrayS, Dto::getParentArrayC)
                .doesNotContainNull()
                .extracting(c -> ((Object[]) c).length)
                .doesNotContain(0);

        assertion
                .extracting(Dto::getCollectionI, Dto::getCollectionS, Dto::getParentCollectionD, Dto::getParentCollectionS, Dto::getParentCollectionS)
                .doesNotContainNull()
                .extracting(c -> ((Collection<?>) c).size())
                .doesNotContain(0);

    }

    public interface DtoFactory {

        Dto<Integer, List<String>> dto();

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Dto<I, S> extends AbstractDto<Double, S, Set<String>> {

        private I objectI;
        private I[] arrayI;
        private Collection<I> collectionI;

        private S objectS;
        private S[] arrayS;
        private Collection<S> collectionS;

        private Dto<? extends S, Integer> bounded;

    }

    @Data
    public static abstract class AbstractDto<T, S, C> {

        private T parentObjectD;
        private T[] parentArrayD;
        private Collection<T> parentCollectionD;

        private S parentObjectS;
        private S[] parentArrayS;
        private Collection<S> parentCollectionS;

        private C parentObjectC;
        private C[] parentArrayC;
        private Collection<C> parentCollectionC;

    }

}
