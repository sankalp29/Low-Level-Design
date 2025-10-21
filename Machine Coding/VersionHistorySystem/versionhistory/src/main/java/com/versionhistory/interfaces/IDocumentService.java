package com.versionhistory.interfaces;

import java.nio.file.AccessDeniedException;

import com.versionhistory.document.DocumentVersion;
import com.versionhistory.exceptions.DocumentVersionException;
import com.versionhistory.user.User;

public interface IDocumentService {

    String createDocument(User user, String title, String content);

    public void updateDocument(User user, String documentId, String title, String content) throws AccessDeniedException, DocumentVersionException;

    void changeDocumentVersion(User user, String documentId, Integer version)
            throws AccessDeniedException, DocumentVersionException;

    void deleteDocument(User user, String documentId) throws AccessDeniedException;

    DocumentVersion getDocument(User user, String documentId) throws AccessDeniedException;
}