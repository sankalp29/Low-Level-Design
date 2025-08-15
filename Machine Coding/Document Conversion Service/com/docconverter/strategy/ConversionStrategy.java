package com.docconverter.strategy;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentConversionException;

public interface ConversionStrategy {
    Document convert(Document document, DocumentFormat documentFormat) throws DocumentConversionException;
    boolean isValidConversion(DocumentFormat from, DocumentFormat to);
    String getConversionDescription();
}
