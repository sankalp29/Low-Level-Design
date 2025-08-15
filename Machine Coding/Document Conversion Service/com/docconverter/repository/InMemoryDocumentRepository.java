package com.docconverter.repository;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentValidationException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryDocumentRepository implements DocumentRepository {
    private final Map<String, Document> documents = new ConcurrentHashMap<>();

    @Override
    public String store(Document document) throws DocumentValidationException {
        documents.put(document.getId(), document);
        return document.getId();
    }

    @Override
    public Optional<Document> findById(String documentId) {
        return Optional.ofNullable(documents.get(documentId));
    }

    @Override
    public List<Document> findByFormat(DocumentFormat format) {
        if (format == null) {
            return Collections.emptyList();
        }
        return documents.values().stream()
                .filter(doc -> format.equals(doc.getDocumentFormat()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> findAll() {
        return new ArrayList<>(documents.values());
    }

    @Override
    public boolean delete(String documentId) {
        return documents.remove(documentId) != null;
    }

    @Override
    public void clear() {
        documents.clear();
    }

    @Override
    public long count() {
        return documents.size();
    }
}
