package com.versionhistory.document;

import java.time.LocalDateTime;

import com.versionhistory.user.Person;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude={"createdAt", "updatedBy"})
public class DocumentVersion {
    private final Integer versionNo;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Person updatedBy;

    public DocumentVersion(Integer versionNo, String title, String content, Person updatedBy) {
        this.versionNo = versionNo;
        this.title = title;
        this.content = content;
        this.updatedBy = updatedBy;
        this.createdAt = LocalDateTime.now();
    }
}
