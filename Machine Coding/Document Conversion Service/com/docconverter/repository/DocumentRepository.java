package com.docconverter.repository;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentValidationException;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    String store(Document document) throws DocumentValidationException;
    Optional<Document> findById(String documentId);
    List<Document> findByFormat(DocumentFormat format);
    List<Document> findAll();
    boolean delete(String documentId);
    void clear();
    long count();
}
