package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.References;
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

    public interface ObjectoFactory {

        @References("childHolder.child.parentHolder.parent")
        Parent createParent();

        @References("parentHolder.parent.childHolder.child")
        Child createChild();

    }

    @Data
    public static class Parent {

        private String name;

        private ChildHolder childHolder;

    }

    @Data
    public static class ChildHolder {

        private Child child;

    }

    @Data
    public static class Child {

        private String name;

        @ToString.Exclude
        private ParentHolder parentHolder;

    }

    @Data
    public static class ParentHolder {

        private Parent parent;

    }

}
