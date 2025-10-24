package com.versionhistory.document;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.versionhistory.user.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Document {
    private final String documentId;
    private final User creator;
    private volatile DocumentVersion activeVersion;
    private ReentrantReadWriteLock lock;

    public Document(User creator) {
        this.documentId = UUID.randomUUID().toString();
        this.creator = creator;
        this.lock = new ReentrantReadWriteLock();
    }

    public void setActiveVersion(DocumentVersion activeVersion) {
        this.activeVersion = activeVersion; 
    }
}