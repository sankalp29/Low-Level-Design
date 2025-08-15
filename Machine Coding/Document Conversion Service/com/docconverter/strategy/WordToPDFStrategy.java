package com.docconverter.strategy;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentConversionException;
import com.docconverter.exception.DocumentValidationException;
import java.util.logging.Logger;

public class WordToPDFStrategy implements ConversionStrategy {
    private static final Logger logger = Logger.getLogger(WordToPDFStrategy.class.getName());

    @Override
    public Document convert(Document document, DocumentFormat targetFormat) throws DocumentConversionException {
        if (document == null) {
            throw new DocumentConversionException("Cannot convert null document", 
                    "WORD", targetFormat.name(), "null");
        }
        
        if (!isValidConversion(document.getDocumentFormat(), targetFormat)) {
            throw new DocumentConversionException("Invalid conversion requested", 
                    document.getDocumentFormat().name(), targetFormat.name(), document.getName());
        }

        logger.info("Converting document from Word to PDF: " + document.getName());
        
        try {
            // Simulate conversion process
            Thread.sleep(100); // Simulate processing time
            
            String convertedContent = simulateWordToPdfConversion(document.getContent());
            Document convertedDoc = new Document(
                document.getName() + "_converted_to_pdf", 
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
        return from == DocumentFormat.WORD && to == DocumentFormat.PDF;
    }
    
    @Override
    public String getConversionDescription() {
        return "Converts Microsoft Word documents to PDF format";
    }
    
    private String simulateWordToPdfConversion(String content) {
        // Simulate conversion logic
        return "[PDF_HEADER]" + content + "[PDF_FOOTER]";
    }
}
