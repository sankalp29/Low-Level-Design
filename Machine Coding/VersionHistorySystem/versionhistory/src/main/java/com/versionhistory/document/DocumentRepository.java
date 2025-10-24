package com.versionhistory.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.interfaces.IDocumentRepository;

public class DocumentRepository implements IDocumentRepository {
    private final Map<String, Document> documents;
    private final Map<String, List<DocumentVersion>> documentVersionHistory;
    private final Map<String, Map<String, AccessLevel>> usersDocAccess;

    @Override
    public void createDocument(Document document, DocumentVersion initialVersion) {
        documents.put(document.getDocumentId(), document);
        documentVersionHistory.put(document.getDocumentId(), new ArrayList<>());
        documentVersionHistory.get(document.getDocumentId()).add(initialVersion);
    }

    @Override
    public void addNewDocumentVersion(String documentId, DocumentVersion documentVersion) {
        documentVersionHistory.get(documentId).add(documentVersion);
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
    public List<DocumentVersion> listDocumentVersions(String documentId) {
        return Collections.unmodifiableList(new ArrayList<>(documentVersionHistory.getOrDefault(documentId, new ArrayList<>())));
    }

    @Override
    public void deleteDocument(String documentId) {
        documents.remove(documentId);
        documentVersionHistory.remove(documentId);
        removeAllAccessForDocument(documentId);
    }

    @Override
    public void setUserAccess(String documentId, String recipient, AccessLevel accessLevel) {
        usersDocAccess.putIfAbsent(recipient, new ConcurrentHashMap<>());
        usersDocAccess.get(recipient).put(documentId, accessLevel);
    }

    @Override
    public void unsetUserAccess(String documentId, String recipient) {
        Map<String, AccessLevel> perUser = usersDocAccess.get(recipient);
        if (perUser != null) {
            perUser.remove(documentId);
            if (perUser.isEmpty()) {
                usersDocAccess.remove(recipient);
            }
        }
    }

    @Override
    public Map<String, AccessLevel> getAccessMap(String userId) {
        return usersDocAccess.getOrDefault(userId, new HashMap<>());
    }

    @Override
    public void removeAllAccessForDocument(String documentId) {
        for (Iterator<Map.Entry<String, Map<String, AccessLevel>>> it = usersDocAccess.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Map<String, AccessLevel>> entry = it.next();
            Map<String, AccessLevel> perUser = entry.getValue();
            perUser.remove(documentId);
            if (perUser.isEmpty()) {
                it.remove();
            }
        }
    }

    public DocumentRepository() {
        this.documents = new ConcurrentHashMap<>();
        this.documentVersionHistory = new ConcurrentHashMap<>();
        this.usersDocAccess = new ConcurrentHashMap<>();
    }
}
