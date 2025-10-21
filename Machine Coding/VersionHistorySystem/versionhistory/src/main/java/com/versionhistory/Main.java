package com.versionhistory;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Set;

import com.versionhistory.document.DocumentRepository;
import com.versionhistory.document.DocumentService;
import com.versionhistory.exceptions.DocumentVersionException;
import com.versionhistory.interfaces.IDocumentService;
import com.versionhistory.user.User;

public class Main {
    public static void main(String[] args) {
        // testCreateDocumentUpdateVersion();
        // testCreateDocumentDeleteDocumentUpdateDocument();
        // testCreateDocumentUpdateDocumentUnauthorized();
        // testCreateDocumentsConcurrently();
        testUpdateDocumentConcurrently();
    }

    private static void testCreateDocumentUpdateVersion() {
        IDocumentService documentService = new DocumentService(new DocumentRepository());
        User user = new User("Sankalp", "sankalp@email.com");
        String documentId = documentService.createDocument(user, "this is the Title", "Some random content....");
        try {
            documentService.updateDocument(user, documentId, "this is the Title", "Updated the content here");
            System.out.println(documentService.getDocument(user, documentId));
        } catch (AccessDeniedException | DocumentVersionException e) {
            e.printStackTrace();
        }
    }

    private static void testCreateDocumentDeleteDocumentUpdateDocument() {
        // DocumentID does not exist exception should occur
        IDocumentService documentService = new DocumentService(new DocumentRepository());
        User user = new User("Sankalp", "sankalp@email.com");
        String documentId = documentService.createDocument(user, "this is the Title", "Some random content....");
        try {
            documentService.deleteDocument(user, documentId);
            documentService.updateDocument(user, documentId, "updated title", "updated content");
        } catch (AccessDeniedException | DocumentVersionException e) {
            e.printStackTrace();
        }
    }

    private static void testCreateDocumentUpdateDocumentUnauthorized() {
        // AccessDeniedException should occur
        IDocumentService documentService = new DocumentService(new DocumentRepository());
        User sankalp = new User("Sankalp", "sankalp@email.com");
        User janvi = new User("Janvi", "janvi@email.com");
        String documentId = documentService.createDocument(sankalp, "this is the Title", "Some random content....");
        try {
            documentService.updateDocument(janvi, documentId, "this is the Title", "Updated the content here");
        } catch (AccessDeniedException | DocumentVersionException e) {
            e.printStackTrace();
        }
    }

    private static void testCreateDocumentsConcurrently() {
        IDocumentService documentService = new DocumentService(new DocumentRepository());
        User user = new User("Sankalp", "sankalp@email.com");
        final String[] document1 = new String[1], document2 = new String[1];
        Runnable runnable1 = () -> document1[0] = documentService.createDocument(user, "Title 1", "Some ONE random content....");
        Runnable runnable2 = () -> document2[0] = documentService.createDocument(user, "Title 2", "Some TWO random content....");
        
        Thread t1 = new Thread(runnable1, "Thread 1");
        Thread t2 = new Thread(runnable2, "Thread 2");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            documentService.updateDocument(user, document1[0], "Title 1 New", "ONE new content");
            documentService.updateDocument(user, document2[0], "Title 2 New", "TWO new content");

            System.out.println(documentService.getDocument(user, document1[0]));
            System.out.println(documentService.getDocument(user, document2[0]));
 
        } catch (InterruptedException | AccessDeniedException | DocumentVersionException e) {
            e.printStackTrace();
        }
    }

    private static void testUpdateDocumentConcurrently() {
        IDocumentService documentService = new DocumentService(new DocumentRepository());
        User user = new User("Sankalp", "sankalp@email.com");
        String documentId = documentService.createDocument(user, "Title", "This is the content of the document.");
        
        Set<Thread> threads = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            final int idx = i;
            Thread t = new Thread(() -> {
                try {
                    documentService.updateDocument(user, documentId, "Updated title " + idx, "Updated content " + idx);
                } catch (AccessDeniedException | DocumentVersionException e) {
                    e.printStackTrace();
                }
            });
            threads.add(t);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Finished : " + documentService.getDocument(user, documentId));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }
    }
}