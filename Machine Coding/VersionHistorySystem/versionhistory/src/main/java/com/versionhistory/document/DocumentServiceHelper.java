package com.versionhistory.document;

import java.util.Map;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.interfaces.IDocumentRepository;
import com.versionhistory.user.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DocumentServiceHelper {
    private final IDocumentRepository documentRepository;
    
    public boolean hasEditAccess(User user, String documentId) {
        Map<String, AccessLevel> accessPerDoc = documentRepository.getAccessMap(user.getUserId());
        return accessPerDoc.containsKey(documentId) && accessPerDoc.get(documentId) == AccessLevel.COLLABORATOR;
    }

    public boolean hasViewAccess(User user, String documentId) {
        Map<String, AccessLevel> accessPerDoc = documentRepository.getAccessMap(user.getUserId());
        return accessPerDoc.containsKey(documentId) && accessPerDoc.get(documentId) != AccessLevel.NONE;
    }
}
