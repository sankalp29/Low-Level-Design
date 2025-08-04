package DocumentParserFactory;

public class DocumentParserFactory {
    public static DocumentParser getParser(ParserType type) throws IllegalArgumentException {
        switch (type) {
            case XML:
                return new XMLParser();
            case JSON:
                return new JSONParser();
            case CSV:
                return new CSVParser();
            default: throw new IllegalArgumentException("Illegal parser option chosen");
        }
    }
}
