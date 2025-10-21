package com.versionhistory.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.interfaces.IDocumentRepository;

public class DocumentRepository implements IDocumentRepository {
    private final Map<String, Document> documents;
    private final Map<String, List<DocumentVersion>> documentVersionHistory;
    private final Map<String, Map<String, AccessLevel>> usersDocAccess;

    @Override
    public void addNewDocumentVersion(Document document, DocumentVersion documentVersion) {
        documents.put(document.getDocumentId(), document);
        documentVersionHistory.putIfAbsent(document.getDocumentId(), new CopyOnWriteArrayList<>());
        documentVersionHistory.get(document.getDocumentId()).add(documentVersion);
    }

    @Override
    public Document getById(String documentId) {
        return documents.get(documentId);
    }

    @Override
    public DocumentVersion getLastDocumentVersion(String documentId) {
        if (getById(documentId) == null) return null;
        Integer size = documentVersionHistory.getOrDefault(documentId, new ArrayList<>()).size();
        return documentVersionHistory.get(documentId).get(size - 1);
    }

    @Override
    public DocumentVersion getDocumentVersionById(String documentId, Integer version) {
        return documentVersionHistory.get(documentId).get(version - 1);
    }

    @Override
    public void deleteDocument(String documentId) {
        documents.remove(documentId);
        documentVersionHistory.remove(documentId);
    }

    @Override
    public void setUserAccess(String documentId, String recipient, AccessLevel accessLevel) {
        usersDocAccess.putIfAbsent(recipient, new ConcurrentHashMap<>());
        usersDocAccess.get(recipient).put(documentId, accessLevel);
    }

    @Override
    public Map<String, AccessLevel> getAccessMap(String userId) {
        return usersDocAccess.getOrDefault(userId, new HashMap<>());
    }

    public DocumentRepository() {
        this.documents = new ConcurrentHashMap<>();
        this.documentVersionHistory = new ConcurrentHashMap<>();
        this.usersDocAccess = new ConcurrentHashMap<>();
    }
}
