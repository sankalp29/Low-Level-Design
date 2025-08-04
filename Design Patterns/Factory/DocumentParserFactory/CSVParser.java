package DocumentParserFactory;

public class CSVParser implements DocumentParser {
    @Override
    public void parse(String text) {
        System.out.println("CSV Parser has parsed : " + text + ".csv");
    }
}
