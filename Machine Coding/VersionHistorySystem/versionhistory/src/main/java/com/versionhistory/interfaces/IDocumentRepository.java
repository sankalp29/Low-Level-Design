package com.versionhistory.interfaces;

import java.util.List;
import java.util.Map;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.document.Document;
import com.versionhistory.document.DocumentVersion;

public interface IDocumentRepository {

    void createDocument(Document document, DocumentVersion initialVersion);

    void addNewDocumentVersion(String documentId, DocumentVersion documentVersion);

    Document getById(String documentId);

    DocumentVersion getLastDocumentVersion(String documentId);

    DocumentVersion getDocumentVersionById(String documentId, Integer version);

    List<DocumentVersion> listDocumentVersions(String documentId);

    void deleteDocument(String documentId);

    void setUserAccess(String documentId, String recipient, AccessLevel accessLevel);

    void unsetUserAccess(String documentId, String recipient);

    void removeAllAccessForDocument(String documentId);

    Map<String, AccessLevel> getAccessMap(String userId);
}