package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.References;
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

    public interface ObjectoFactory {

        @References("child.parent")
        Parent createParent();

        @References("parent.child")
        Child createChild();

    }

    @Data
    public static class Parent {

        private String name;

        private Child child;

    }

    @Data
    public static class Child {

        private String name;

        @ToString.Exclude
        private Parent parent;

    }

}
