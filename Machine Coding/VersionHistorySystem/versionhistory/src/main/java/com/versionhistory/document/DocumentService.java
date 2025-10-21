package com.versionhistory.document;

import java.nio.file.AccessDeniedException;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.exceptions.DocumentVersionException;
import com.versionhistory.interfaces.IDocumentRepository;
import com.versionhistory.interfaces.IDocumentService;
import com.versionhistory.user.User;

public class DocumentService implements IDocumentService {
    private final IDocumentRepository documentRepository;
    private final DocumentServiceHelper documentServiceHelper;

    @Override
    public String createDocument(User user, String title, String content) {
        if (user == null) throw new IllegalArgumentException("User cannot be empty");
        Document document = new Document(user);
        DocumentVersion documentVersion = new DocumentVersion(1, title, content, user);
        documentRepository.addNewDocumentVersion(document, documentVersion);
        document.setActiveVersion(documentVersion);

        return document.getDocumentId();
    }

    @Override
    public void updateDocument(User user, String documentId, String title, String content) throws AccessDeniedException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new IllegalArgumentException("Invalid documentId specified. documentId does not exist");
        if (!user.getUserId().equals(document.getCreator().getUserId()) && !documentServiceHelper.hasEditAccess(user, documentId)) 
            throw new AccessDeniedException("You do not have the access to update this document");
        
        try {
            document.getLock().writeLock().lock();
            DocumentVersion lastVersion = documentRepository.getLastDocumentVersion(documentId);
            if (lastVersion == null) throw new DocumentVersionException("Document does not exist");
            DocumentVersion documentVersion = new DocumentVersion(lastVersion.getVersionNo() + 1, title, content, user);
            documentRepository.addNewDocumentVersion(document, documentVersion);
            document.setActiveVersion(documentVersion);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public void changeDocumentVersion(User user, String documentId, Integer version) throws AccessDeniedException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new IllegalArgumentException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId())) throw new AccessDeniedException("You do not have the access to update this document");
        
        try {
            document.getLock().writeLock().lock();
            DocumentVersion lastVersion = documentRepository.getLastDocumentVersion(documentId);
            if (version > lastVersion.getVersionNo()) throw new DocumentVersionException("Requested document version does not exist.");
            DocumentVersion documentVersion = documentRepository.getDocumentVersionById(documentId, version);
            document.setActiveVersion(documentVersion);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public void deleteDocument(User user, String documentId) throws AccessDeniedException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new IllegalArgumentException("Invalid documentId specified. documentId does not exist");
        document.getLock().writeLock().lock();
        try {
            if (!document.getCreator().getUserId().equals(user.getUserId())) throw new AccessDeniedException("You do not have the access to update this document");
            documentRepository.deleteDocument(documentId);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public DocumentVersion getDocument(User user, String documentId) throws AccessDeniedException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new IllegalArgumentException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId()) && !documentServiceHelper.hasViewAccess(user, documentId)) 
            throw new AccessDeniedException("You do not have the access to update this document");
        
        return document.getActiveVersion();
    }

    public void setUserAccess(User owner, String documentId, User recipient, AccessLevel accessLevel) throws AccessDeniedException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new IllegalArgumentException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(owner.getUserId())) throw new AccessDeniedException("You do not have the access to update this document");
        if (document.getCreator().getUserId().equals(recipient.getUserId())) return;

        documentRepository.setUserAccess(documentId, recipient.getUserId(), accessLevel);
    }

    public DocumentService(IDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.documentServiceHelper = new DocumentServiceHelper(documentRepository);
    }
}