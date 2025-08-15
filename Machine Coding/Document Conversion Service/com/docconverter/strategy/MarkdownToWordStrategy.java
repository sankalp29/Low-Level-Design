package com.docconverter.strategy;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentConversionException;
import com.docconverter.exception.DocumentValidationException;
import java.util.logging.Logger;

public class MarkdownToWordStrategy implements ConversionStrategy {
    private static final Logger logger = Logger.getLogger(MarkdownToWordStrategy.class.getName());

    @Override
    public Document convert(Document document, DocumentFormat targetFormat) throws DocumentConversionException {
        if (document == null) {
            throw new DocumentConversionException("Cannot convert null document", 
                    "MARKDOWN", targetFormat.name(), "null");
        }
        
        if (!isValidConversion(document.getDocumentFormat(), targetFormat)) {
            throw new DocumentConversionException("Invalid conversion requested", 
                    document.getDocumentFormat().name(), targetFormat.name(), document.getName());
        }

        logger.info("Converting document from Markdown to Word: " + document.getName());
        
        try {
            Thread.sleep(100); // Simulate processing time
            
            String convertedContent = simulateMarkdownToWordConversion(document.getContent());
            Document convertedDoc = new Document(
                document.getName() + "_converted_to_word", 
                convertedContent, 
                targetFormat
            );
            
            logger.info("Successfully converted document: " + document.getName());
            return convertedDoc;
            
        } catch (DocumentValidationException e) {
            throw new DocumentConversionException("Validation failed during conversion", e,
                    document.getDocumentFormat().name(), targetFormat.name(), document.getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DocumentConversionException("Conversion interrupted", e,
                    document.getDocumentFormat().name(), targetFormat.name(), document.getName());
        } catch (Exception e) {
            throw new DocumentConversionException("Conversion failed due to unexpected error", e,
                    document.getDocumentFormat().name(), targetFormat.name(), document.getName());
        }
    }
    
    @Override
    public boolean isValidConversion(DocumentFormat from, DocumentFormat to) {
        return from == DocumentFormat.MARKDOWN && to == DocumentFormat.WORD;
    }
    
    @Override
    public String getConversionDescription() {
        return "Converts Markdown documents to Microsoft Word format";
    }
    
    private String simulateMarkdownToWordConversion(String content) {
        // Simulate conversion logic - convert markdown syntax to Word
        String converted = content.replace("# ", "[HEADING1]").replace("## ", "[HEADING2]").replace("**", "[BOLD]");
        return "[WORD_HEADER]" + converted + "[WORD_FOOTER]";
    }
}
