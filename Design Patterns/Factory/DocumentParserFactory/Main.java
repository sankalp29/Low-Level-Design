package DocumentParserFactory;

public class Main {
    public static void main(String[] args) {
        DocumentParser documentParser = DocumentParserFactory.getParser(ParserType.CSV);
        documentParser.parse("text to parse");
    }
}
