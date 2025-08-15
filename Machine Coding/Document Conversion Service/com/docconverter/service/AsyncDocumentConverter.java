package com.docconverter.service;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.client.DocumentConverterClient;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class AsyncDocumentConverter {
    private static final Logger logger = Logger.getLogger(AsyncDocumentConverter.class.getName());
    private final DocumentConverterClient client;
    private final ExecutorService executorService;
    
    public AsyncDocumentConverter(DocumentConverterClient client) {
        this.client = client;
        this.executorService = Executors.newFixedThreadPool(4);
    }
    
    public CompletableFuture<Document> convertDocumentAsync(String documentId, DocumentFormat targetFormat) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Starting async conversion for document: " + documentId);
                return client.convertDocument(documentId, targetFormat);
            } catch (Exception e) {
                logger.severe("Async conversion failed for document: " + documentId + " - " + e.getMessage());
                throw new RuntimeException("Async conversion failed", e);
            }
        }, executorService);
    }
    
    public CompletableFuture<String> uploadDocumentAsync(Document document) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("Starting async upload for document: " + document.getName());
                return client.uploadDocument(document);
            } catch (Exception e) {
                logger.severe("Async upload failed for document: " + document.getName() + " - " + e.getMessage());
                throw new RuntimeException("Async upload failed", e);
            }
        }, executorService);
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
