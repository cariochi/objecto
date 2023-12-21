package com.cariochi.objecto;

import java.util.Collection;
import java.util.Set;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenericTypeGeneratorTest {

    @Test
    void should_generate_dto() {
        final DtoFactory factory = Objecto.create(DtoFactory.class);
        final Dto<Integer, String> dto = factory.dto();

        assertThat(dto).isNotNull()
                .extracting(Dto::getObjectI, Dto::getObjectS, Dto::getParentObjectD, Dto::getParentObjectS, Dto::getParentObjectC)
                .doesNotContainNull();

        assertThat(dto)
                .extracting(Dto::getArrayI, Dto::getArrayS, Dto::getParentArrayD, Dto::getParentArrayS, Dto::getParentArrayC)
                .doesNotContainNull()
                .extracting(c -> ((Object[]) c).length)
                .doesNotContain(0);

        assertThat(dto)
                .extracting(Dto::getCollectionI, Dto::getCollectionS, Dto::getParetnCollectionD, Dto::getParetnCollectionS, Dto::getParetnCollectionS)
                .doesNotContainNull()
                .extracting(c -> ((Collection<?>) c).size())
                .doesNotContain(0);

    }

    public interface DtoFactory {

        Dto<Integer, String> dto();

    }

    @Data
    public static class Dto<I, S> extends AbstractDto<Double, S, Set<String>> {

        private I objectI;
        private I[] arrayI;
        private Collection<I> collectionI;

        private S objectS;
        private S[] arrayS;
        private Collection<S> collectionS;

    }

    @Data
    public static abstract class AbstractDto<T, S, C> {

        private T parentObjectD;
        private T[] parentArrayD;
        private Collection<T> paretnCollectionD;

        private S parentObjectS;
        private S[] parentArrayS;
        private Collection<S> paretnCollectionS;

        private C parentObjectC;
        private C[] parentArrayC;
        private Collection<C> paretnCollectionC;

    }

}
