package com.docconverter.strategy;

import com.docconverter.model.Document;
import com.docconverter.model.DocumentFormat;
import com.docconverter.exception.DocumentConversionException;
import com.docconverter.exception.DocumentValidationException;
import java.util.logging.Logger;

public class WordToMarkdownStrategy implements ConversionStrategy {
    private static final Logger logger = Logger.getLogger(WordToMarkdownStrategy.class.getName());

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

        logger.info("Converting document from Word to Markdown: " + document.getName());
        
        try {
            Thread.sleep(100); // Simulate processing time
            
            String convertedContent = simulateWordToMarkdownConversion(document.getContent());
            Document convertedDoc = new Document(
                document.getName() + "_converted_to_markdown", 
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
        return from == DocumentFormat.WORD && to == DocumentFormat.MARKDOWN;
    }
    
    @Override
    public String getConversionDescription() {
        return "Converts Microsoft Word documents to Markdown format";
    }
    
    private String simulateWordToMarkdownConversion(String content) {
        // Simulate conversion logic - convert Word formatting to markdown
        String converted = content.replace("[WORD_HEADER]", "").replace("[WORD_FOOTER]", "")
                                  .replace("[HEADING1]", "# ").replace("[HEADING2]", "## ")
                                  .replace("[BOLD]", "**");
        return converted;
    }
}
