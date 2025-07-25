package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.Reference;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneToOneWithChainTest {

    private final ObjectoFactory factory = Objecto.create(ObjectoFactory.class);

    @Test
    void test() {
        final Parent parent = factory.createParent();

        assertThat(parent.getChildHolder().getChild().getParentHolder().getParent())
                .isEqualTo(parent);

        final Child child = factory.createChild();

        assertThat(child.getParentHolder().getParent().getChildHolder().getChild())
                .isEqualTo(child);

    }

    private interface ObjectoFactory {

        @Reference("childHolder.child.parentHolder.parent")
        Parent createParent();

        //        @Reference("parentHolder.parent.childHolder.child")
        Child createChild();

    }

    @Data
    private static class Parent {

        private String name;

        private ChildHolder childHolder;

    }

    @Data
    private static class ChildHolder {

        private Child child;

    }

    @Data
    private static class Child {

        private String name;

        @ToString.Exclude
        private ParentHolder parentHolder;

    }

    @Data
    private static class ParentHolder {

        private Parent parent;

    }

}
