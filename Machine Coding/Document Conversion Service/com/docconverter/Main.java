package com.docconverter;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.client.DocumentConverterClient;
import com.docconverter.service.AsyncDocumentConverter;
import com.docconverter.exception.DocumentConversionException;
import com.docconverter.exception.DocumentValidationException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        try {
            // Create documents with proper error handling
            Document document1 = new Document("question.docx", "Two Sum Algorithm Problem", DocumentFormat.WORD);
            Document document2 = new Document("ITR_2025.pdf", "2025-26 Income Tax Return", DocumentFormat.PDF);
            Document document3 = new Document("answer.md", "# Two Sum - Solution\n\nThis is the solution.", DocumentFormat.MARKDOWN);
            
            // Initialize client
            DocumentConverterClient client = new DocumentConverterClient();
            AsyncDocumentConverter asyncConverter = new AsyncDocumentConverter(client);
            
            // Upload documents
            String doc1Id = client.uploadDocument(document1);
            String doc2Id = client.uploadDocument(document2);
            String doc3Id = client.uploadDocument(document3);
            
            System.out.println("Uploaded documents:");
            System.out.println("Document 1 ID: " + doc1Id);
            System.out.println("Document 2 ID: " + doc2Id);
            System.out.println("Document 3 ID: " + doc3Id);
            
            // Synchronous conversion
            System.out.println("\n=== Synchronous Conversions ===");
            Document convertedDoc = client.convertDocument(doc1Id, DocumentFormat.PDF);
            System.out.println("Converted document: " + convertedDoc);
            
            // Asynchronous conversion demo
            System.out.println("\n=== Asynchronous Conversions ===");
            CompletableFuture<Document> futureConversion = asyncConverter.convertDocumentAsync(doc3Id, DocumentFormat.WORD);
            
            futureConversion.thenAccept(result -> {
                System.out.println("Async conversion completed: " + result);
            }).exceptionally(throwable -> {
                System.err.println("Async conversion failed: " + throwable.getMessage());
                return null;
            });
            
            // Wait for async operation to complete
            Thread.sleep(2000);
            
            // Clean up
            asyncConverter.shutdown();
            
        } catch (DocumentValidationException e) {
            logger.severe("Document validation error: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        } catch (DocumentConversionException e) {
            logger.severe("Document conversion error: " + e.getMessage());
            System.err.println("Conversion Error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
