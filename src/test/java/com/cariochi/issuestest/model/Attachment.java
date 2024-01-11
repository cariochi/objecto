package com.cariochi.issuestest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Attachment<T> {

    private String fileName;
    private T fileContent;

}
