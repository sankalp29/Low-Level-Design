package com.versionhistory.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.versionhistory.constants.AccessLevel;
import com.versionhistory.dto.DocumentDTO;
import com.versionhistory.exceptions.AuthorizationException;
import com.versionhistory.exceptions.DocumentVersionException;
import com.versionhistory.exceptions.NotFoundException;
import com.versionhistory.exceptions.ValidationException;
import com.versionhistory.interfaces.IDocumentRepository;
import com.versionhistory.interfaces.IDocumentService;
import com.versionhistory.user.User;

public class DocumentService implements IDocumentService {
    private final IDocumentRepository documentRepository;
    private final DocumentServiceHelper documentServiceHelper;

    @Override
    public String createDocument(User user, String title, String content) throws ValidationException {
        if (user == null) throw new ValidationException("User cannot be empty");
        if (title == null || title.isBlank()) throw new ValidationException("Title cannot be empty");
        if (content == null) throw new ValidationException("Content cannot be null");
        Document document = new Document(user);
        DocumentVersion documentVersion = new DocumentVersion(1, title, content, user);
        documentRepository.createDocument(document, documentVersion);
        document.setActiveVersion(documentVersion);

        return document.getDocumentId();
    }

    @Override
    public void updateDocument(User user, String documentId, String title, String content) throws AuthorizationException, NotFoundException, ValidationException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (title == null || title.isBlank()) throw new ValidationException("Title cannot be empty");
        if (content == null) throw new ValidationException("Content cannot be null");
        if (!user.getUserId().equals(document.getCreator().getUserId()) && !documentServiceHelper.hasEditAccess(user, documentId)) 
            throw new AuthorizationException("You do not have access to update this document");
        
        try {
            document.getLock().writeLock().lock();
            DocumentVersion lastVersion = documentRepository.getLastDocumentVersion(documentId);
            if (lastVersion == null) throw new DocumentVersionException("Document does not exist");
            DocumentVersion documentVersion = new DocumentVersion(lastVersion.getVersionNo() + 1, title, content, user);
            documentRepository.addNewDocumentVersion(documentId, documentVersion);
            document.setActiveVersion(documentVersion);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public void changeDocumentVersion(User user, String documentId, Integer version) throws AuthorizationException, NotFoundException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId())) throw new AuthorizationException("You do not have access to change the document version");
        
        try {
            document.getLock().writeLock().lock();
            DocumentVersion lastVersion = documentRepository.getLastDocumentVersion(documentId);
            if (version == null || version < 1 || version > lastVersion.getVersionNo()) throw new DocumentVersionException("Requested document version does not exist.");
            DocumentVersion documentVersion = documentRepository.getDocumentVersionById(documentId, version);
            document.setActiveVersion(documentVersion);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public void deleteDocument(User user, String documentId) throws AuthorizationException, NotFoundException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        document.getLock().writeLock().lock();
        try {
            if (!document.getCreator().getUserId().equals(user.getUserId())) throw new AuthorizationException("You do not have access to delete this document");
            documentRepository.deleteDocument(documentId);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public DocumentDTO getDocument(User user, String documentId) throws AuthorizationException, NotFoundException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId()) && !documentServiceHelper.hasViewAccess(user, documentId)) 
            throw new AuthorizationException("You do not have access to view this document");

        DocumentVersion active;
        try {
            document.getLock().readLock().lock();
            active = document.getActiveVersion();
        } finally {
            document.getLock().readLock().unlock();
        }
        return new DocumentDTO(
            document.getDocumentId(),
            active.getVersionNo(),
            active.getTitle(),
            active.getContent(),
            active.getCreatedAt(),
            active.getUpdatedBy().getUserId(),
            active.getUpdatedBy().getName()
        );
    }

    @Override
    public void setUserAccess(User owner, String documentId, User recipient, AccessLevel accessLevel) throws AuthorizationException, NotFoundException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(owner.getUserId())) throw new AuthorizationException("You do not have access to modify sharing for this document");
        if (document.getCreator().getUserId().equals(recipient.getUserId())) return;

        documentRepository.setUserAccess(documentId, recipient.getUserId(), accessLevel);
    }

    @Override
    public List<DocumentVersion> listVersions(User user, String documentId) throws AuthorizationException, NotFoundException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId()) && !documentServiceHelper.hasViewAccess(user, documentId)) 
            throw new AuthorizationException("You do not have access to view versions of this document");
        try {
            document.getLock().readLock().lock();
            return documentRepository.listDocumentVersions(documentId);
        } finally {
            document.getLock().readLock().unlock();
        }
    }

    @Override
    public List<DocumentVersion> listVersions(User user, String documentId, int offset, int limit) throws AuthorizationException, NotFoundException, ValidationException {
        if (offset < 0) throw new ValidationException("offset must be >= 0");
        if (limit <= 0) throw new ValidationException("limit must be > 0");
        List<DocumentVersion> all = listVersions(user, documentId);
        int from = Math.min(offset, all.size());
        int to = Math.min(from + limit, all.size());
        return Collections.unmodifiableList(new ArrayList<>(all.subList(from, to)));
    }

    @Override
    public DocumentVersion getVersion(User user, String documentId, Integer version) throws AuthorizationException, NotFoundException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId()) && !documentServiceHelper.hasViewAccess(user, documentId)) 
            throw new AuthorizationException("You do not have access to view this document version");
        if (version == null || version < 1) throw new DocumentVersionException("Requested document version does not exist.");
        try {
            document.getLock().readLock().lock();
            DocumentVersion lastVersion = documentRepository.getLastDocumentVersion(documentId);
            if (version > lastVersion.getVersionNo()) throw new DocumentVersionException("Requested document version does not exist.");
            return documentRepository.getDocumentVersionById(documentId, version);
        } finally {
            document.getLock().readLock().unlock();
        }
    }

    @Override
    public void revertToVersion(User user, String documentId, Integer version) throws AuthorizationException, NotFoundException, DocumentVersionException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(user.getUserId()) && !documentServiceHelper.hasEditAccess(user, documentId)) 
            throw new AuthorizationException("You do not have access to update this document");
        if (version == null || version < 1) throw new DocumentVersionException("Requested document version does not exist.");
        try {
            document.getLock().writeLock().lock();
            DocumentVersion last = documentRepository.getLastDocumentVersion(documentId);
            if (version > last.getVersionNo()) throw new DocumentVersionException("Requested document version does not exist.");
            DocumentVersion target = documentRepository.getDocumentVersionById(documentId, version);
            DocumentVersion newHead = new DocumentVersion(last.getVersionNo() + 1, target.getTitle(), target.getContent(), user);
            documentRepository.addNewDocumentVersion(documentId, newHead);
            document.setActiveVersion(newHead);
        } finally {
            document.getLock().writeLock().unlock();
        }
    }

    @Override
    public void unsetUserAccess(User owner, String documentId, User recipient) throws AuthorizationException, NotFoundException {
        Document document = documentRepository.getById(documentId);
        if (document == null) throw new NotFoundException("Invalid documentId specified. documentId does not exist");
        if (!document.getCreator().getUserId().equals(owner.getUserId())) throw new AuthorizationException("You do not have access to modify sharing for this document");
        if (document.getCreator().getUserId().equals(recipient.getUserId())) return;
        documentRepository.unsetUserAccess(documentId, recipient.getUserId());
    }

    public DocumentService(IDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.documentServiceHelper = new DocumentServiceHelper(documentRepository);
    }
}