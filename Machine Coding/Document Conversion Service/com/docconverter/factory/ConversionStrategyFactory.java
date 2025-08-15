package com.docconverter.factory;

import com.docconverter.model.DocumentFormat;
import com.docconverter.strategy.*;
import com.docconverter.exception.DocumentConversionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ConversionStrategyFactory {
    private static final Logger logger = Logger.getLogger(ConversionStrategyFactory.class.getName());
    private final Map<String, ConversionStrategy> conversionStrategies = new HashMap<>();

    public ConversionStrategy getConversionStrategy(DocumentFormat originalFormat, DocumentFormat convertTo) 
            throws DocumentConversionException {
        
        if (originalFormat == null || convertTo == null) {
            throw new DocumentConversionException("Document formats cannot be null", 
                    originalFormat != null ? originalFormat.name() : "null", 
                    convertTo != null ? convertTo.name() : "null", 
                    "unknown");
        }
        
        // Note: Same-format check removed - handled at client level for better UX
        
        String conversionKey = key(originalFormat, convertTo);
        ConversionStrategy strategy = conversionStrategies.get(conversionKey);
        
        if (strategy == null) {
            logger.warning("Unsupported conversion requested: " + originalFormat + " to " + convertTo);
            throw new DocumentConversionException("Conversion from " + originalFormat + " to " + convertTo + " not supported", 
                    originalFormat.name(), convertTo.name(), "unknown");
        }
        
        return strategy;
    }

    public ConversionStrategyFactory() {
        initializeStrategies();
        logger.info("ConversionStrategyFactory initialized with " + conversionStrategies.size() + " strategies");
    }
    
    private void initializeStrategies() {
        conversionStrategies.put(key(DocumentFormat.WORD, DocumentFormat.PDF), new WordToPDFStrategy());
        conversionStrategies.put(key(DocumentFormat.PDF, DocumentFormat.WORD), new PDFToWordStrategy());
        conversionStrategies.put(key(DocumentFormat.PDF, DocumentFormat.MARKDOWN), new PDFToMarkdownStrategy());
        conversionStrategies.put(key(DocumentFormat.MARKDOWN, DocumentFormat.PDF), new MarkdownToPDFStrategy());
        conversionStrategies.put(key(DocumentFormat.MARKDOWN, DocumentFormat.WORD), new MarkdownToWordStrategy());
        conversionStrategies.put(key(DocumentFormat.WORD, DocumentFormat.MARKDOWN), new WordToMarkdownStrategy());
    }
    
    public boolean isConversionSupported(DocumentFormat from, DocumentFormat to) {
        if (from == null || to == null) {
            return false;
        }
        // Note: Same-format conversions not supported by design
        if (from == to) {
            return false;
        }
        return conversionStrategies.containsKey(key(from, to));
    }
    
    public Set<String> getSupportedConversions() {
        return conversionStrategies.keySet();
    }

    private String key(DocumentFormat from, DocumentFormat to) {
        return from.name() + " : " + to.name();
    }
}
