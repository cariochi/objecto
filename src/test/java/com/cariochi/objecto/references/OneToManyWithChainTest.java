package com.cariochi.objecto.references;

import com.cariochi.objecto.Objecto;
import com.cariochi.objecto.Reference;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneToManyWithChainTest {

    private final ObjectoFactory factory = Objecto.create(ObjectoFactory.class);

    @Test
    void test() {
        final Parent parent = factory.createParent();

        assertThat(parent.getChildrenHolders())
                .flatMap(ChildrenHolder::getChildren)
                .extracting(Child::getParent)
                .containsOnly(parent);

        final Child child = factory.createChild();

        assertThat(child.getParent().getChildrenHolders())
                .extracting(holder -> holder.getChildren().get(0))
                .containsOnly(child);
        assertThat(child.getParent().getChildrenHolders())
                .extracting(holder -> holder.getChildren().get(1))
                .doesNotContain(child);

        assertThat(child.getParent().getChildrenHolders())
                .flatMap(ChildrenHolder::getChildren)
                .extracting(Child::getParent)
                .containsOnly(child.getParent());

    }

    private interface ObjectoFactory {

        @Reference("childrenHolders[*].children[*].parent")
        Parent createParent();

        //        @Reference("parent.childrenHolders[*].children[*]")
        Child createChild();

    }

    @Data
    private static class Parent {

        private String name;

        private List<ChildrenHolder> childrenHolders;

    }

    @Data
    private static class ChildrenHolder {

        private List<Child> children;

    }

    @Data
    private static class Child {

        private String name;

        @ToString.Exclude
        private Parent parent;

    }

}
