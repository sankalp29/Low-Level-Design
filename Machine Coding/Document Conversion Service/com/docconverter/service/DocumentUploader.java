package com.docconverter.service;

import com.docconverter.model.Document;
import com.docconverter.repository.DocumentRepository;
import com.docconverter.exception.DocumentValidationException;
import java.util.Optional;
import java.util.logging.Logger;

public class DocumentUploader {
    private static final Logger logger = Logger.getLogger(DocumentUploader.class.getName());
    private final DocumentRepository repository;

    public DocumentUploader(DocumentRepository repository) {
        this.repository = repository;
    }

    public String uploadDocument(Document document) throws DocumentValidationException {
        if (document == null) {
            throw new DocumentValidationException("Cannot upload null document");
        }
        
        logger.info("Uploading document: " + document.getName());
        
        try {
            String documentId = repository.store(document);
            logger.info("Successfully uploaded document with ID: " + documentId);
            return documentId;
        } catch (Exception e) {
            logger.severe("Failed to upload document: " + document.getName() + " - " + e.getMessage());
            throw new DocumentValidationException("Failed to upload document: " + e.getMessage(), e);
        }
    }

    public Optional<Document> getDocument(String documentId) {
        if (documentId == null || documentId.trim().isEmpty()) {
            logger.warning("Attempted to retrieve document with null/empty ID");
            return Optional.empty();
        }
        
        return repository.findById(documentId);
    }
    
    public boolean deleteDocument(String documentId) {
        if (documentId == null || documentId.trim().isEmpty()) {
            logger.warning("Attempted to delete document with null/empty ID");
            return false;
        }
        
        boolean deleted = repository.delete(documentId);
        if (deleted) {
            logger.info("Successfully deleted document with ID: " + documentId);
        } else {
            logger.warning("Failed to delete document with ID: " + documentId);
        }
        return deleted;
    }
}
