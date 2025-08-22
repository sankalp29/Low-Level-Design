package com.docconverter.repository;

import com.docconverter.model.Document;
import com.docconverter.exception.DocumentValidationException;
import java.util.Optional;

public interface DocumentRepository {
    String store(Document document) throws DocumentValidationException;
    Optional<Document> findById(String documentId);
    boolean delete(String documentId);
    void clear();
}
