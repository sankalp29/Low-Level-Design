package com.docconverter.exception;

public class DocumentConversionException extends Exception {
    private final String sourceFormat;
    private final String targetFormat;
    private final String documentName;

    public DocumentConversionException(String message, String sourceFormat, String targetFormat, String documentName) {
        super(message);
        this.sourceFormat = sourceFormat;
        this.targetFormat = targetFormat;
        this.documentName = documentName;
    }

    public DocumentConversionException(String message, Throwable cause, String sourceFormat, String targetFormat, String documentName) {
        super(message, cause);
        this.sourceFormat = sourceFormat;
        this.targetFormat = targetFormat;
        this.documentName = documentName;
    }

    public String getSourceFormat() { return sourceFormat; }
    public String getTargetFormat() { return targetFormat; }
    public String getDocumentName() { return documentName; }

    @Override
    public String toString() {
        return String.format("DocumentConversionException: %s [Document: %s, %s -> %s]", 
                           getMessage(), documentName, sourceFormat, targetFormat);
    }
}
