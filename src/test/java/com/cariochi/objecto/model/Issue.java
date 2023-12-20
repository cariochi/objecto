package com.cariochi.objecto.model;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    private String key;
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

    private List<Issue> subtasks;
    private Issue parent;

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


}
