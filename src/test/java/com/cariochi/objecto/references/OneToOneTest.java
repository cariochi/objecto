package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.Reference;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneToOneTest {

    private final ObjectoFactory factory = Objecto.create(ObjectoFactory.class);

    @Test
    void test() {
        final Parent parent = factory.createParent();

        assertThat(parent.getChild().getParent())
                .isEqualTo(parent);

        final Child child = factory.createChild();

        assertThat(child.getParent().getChild())
                .isEqualTo(child);

    }

    private interface ObjectoFactory {

        @Reference("child.parent")
        Parent createParent();

        //        @Reference("parent.child")
        Child createChild();

    }

    @Data
    private static class Parent {

        private String name;

        private Child child;

    }

    @Data
    private static class Child {

        private String name;

        @ToString.Exclude
        private Parent parent;

    }

}
