package com.docconverter.model;

import com.docconverter.exception.DocumentValidationException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Document {
    private final String id;
    private final String name;
    private final String content;
    private final DocumentFormat documentFormat;
    private final LocalDateTime createdAt;
    private final long sizeInBytes;
    
    public Document(String name, String content, DocumentFormat documentFormat) throws DocumentValidationException {
        this.id = generateId();
        this.name = validateAndSetName(name);
        this.content = validateAndSetContent(content);
        this.documentFormat = validateAndSetFormat(documentFormat);
        this.createdAt = LocalDateTime.now();
        this.sizeInBytes = content != null ? content.getBytes().length : 0;
    }
    
    private String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
    
    private String validateAndSetName(String name) throws DocumentValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new DocumentValidationException("Document name cannot be null or empty");
        }
        if (name.length() > 255) {
            throw new DocumentValidationException("Document name cannot exceed 255 characters");
        }
        return name.trim();
    }
    
    private String validateAndSetContent(String content) throws DocumentValidationException {
        if (content == null) {
            throw new DocumentValidationException("Document content cannot be null");
        }
        if (content.length() > 10_000_000) { // 10MB limit
            throw new DocumentValidationException("Document content exceeds maximum size limit");
        }
        return content;
    }
    
    private DocumentFormat validateAndSetFormat(DocumentFormat format) throws DocumentValidationException {
        if (format == null) {
            throw new DocumentValidationException("Document format cannot be null");
        }
        return format;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public DocumentFormat getDocumentFormat() {
        return documentFormat;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public long getSizeInBytes() {
        return sizeInBytes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Document document = (Document) obj;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Document{id='%s', name='%s', format=%s, size=%d bytes, createdAt=%s}", 
                           id, name, documentFormat, sizeInBytes, createdAt);
    }
}
