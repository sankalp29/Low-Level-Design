package com.versionhistory.interfaces;

import java.util.Map;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.document.Document;
import com.versionhistory.document.DocumentVersion;

public interface IDocumentRepository {

    void addNewDocumentVersion(Document document, DocumentVersion documentVersion);

    Document getById(String documentId);

    DocumentVersion getLastDocumentVersion(String documentId);

    DocumentVersion getDocumentVersionById(String documentId, Integer version);

    void deleteDocument(String documentId);

    void setUserAccess(String documentId, String recipient, AccessLevel accessLevel);

    Map<String, AccessLevel> getAccessMap(String userId);
}