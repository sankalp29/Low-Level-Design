package com.versionhistory.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DocumentDTO {
    private final String documentId;
    private final Integer activeVersionNo;
    private final String title;
    private final String content;
    private final LocalDateTime updatedAt;
    private final String updatedByUserId;
    private final String updatedByName;
}


