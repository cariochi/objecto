package com.cariochi.objecto.issues.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Issue {

    private final String key;
    private String summary;
    private String description;
    private List<String> labels;

    private User assignee;
    private Type type;
    private Status status;

    private List<Comment> comments;
    private List<Attachment<?>> attachments;

    private Instant creationDate;
    private User createdBy;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Issue> subtasks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Issue parent;

    private Map<DependencyType, Issue> dependencies;

    private Properties properties;

    public enum Type {
        STORY,
        BUG,
        TASK
    }

    public enum Status {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        CLOSED
    }

    public enum DependencyType {
        BLOCK,
        BLOCKED_BY,
        RELATES_TO,
        DUPLICATE,
        DUPLICATED_BY,
        LINKED,
        CLONES,
        IS_CLONED_BY,
        DEPENDS_ON,
        IS_DEPENDENCY_OF,
        EPIC_LINKS,
        HAS_EPIC
    }


    @Data
    @RequiredArgsConstructor(staticName = "of")
    public static class Properties {

        private final String value;
        private int size;

    }

}
