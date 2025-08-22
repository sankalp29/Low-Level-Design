package com.docconverter.client;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.service.DocumentUploader;
import com.docconverter.factory.ConversionStrategyFactory;
import com.docconverter.repository.DocumentRepository;
import com.docconverter.repository.InMemoryDocumentRepository;
import com.docconverter.strategy.ConversionStrategy;
import com.docconverter.exception.DocumentConversionException;
import com.docconverter.exception.DocumentValidationException;
import java.util.Optional;
import java.util.logging.Logger;

public class DocumentConverterClient {
    private static final Logger logger = Logger.getLogger(DocumentConverterClient.class.getName());
    private final DocumentUploader documentUploader;
    private final ConversionStrategyFactory conversionStrategyFactory;
    private final DocumentRepository repository;
    
    public DocumentConverterClient() {
        this.repository = new InMemoryDocumentRepository();
        this.documentUploader = new DocumentUploader(repository);
        this.conversionStrategyFactory = new ConversionStrategyFactory();
    }

    public String uploadDocument(Document document) throws DocumentValidationException {
        logger.info("Client uploading document: " + (document != null ? document.getName() : "null"));
        return documentUploader.uploadDocument(document);
    }

    public Document convertDocument(String documentId, DocumentFormat targetFormat) 
            throws DocumentConversionException, DocumentValidationException {
        
        if (documentId == null || documentId.trim().isEmpty()) {
            throw new DocumentValidationException("Document ID cannot be null or empty");
        }
        
        if (targetFormat == null) {
            throw new DocumentValidationException("Target format cannot be null");
        }
        
        Optional<Document> documentOpt = documentUploader.getDocument(documentId);
        if (!documentOpt.isPresent()) {
            throw new DocumentValidationException("Document with ID " + documentId + " not found");
        }
        
        Document document = documentOpt.get();
        
        // Check if conversion is to the same format
        if (document.getDocumentFormat() == targetFormat) {
            logger.info("Document is already in target format: " + targetFormat);
            return document;
        }
        
        ConversionStrategy strategy = conversionStrategyFactory.getConversionStrategy(document.getDocumentFormat(), targetFormat);

        Document convertedDocument = strategy.convert(document, targetFormat);
        
        // Store the converted document
        String convertedDocId = documentUploader.uploadDocument(convertedDocument);
        logger.info("Converted document stored with ID: " + convertedDocId);
        
        return convertedDocument;
    }
    
    public boolean deleteDocument(String documentId) {
        return documentUploader.deleteDocument(documentId);
    }
}
