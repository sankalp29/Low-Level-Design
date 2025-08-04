package DocumentParserFactory;

public class JSONParser implements DocumentParser {
    @Override
    public void parse(String text) {
        System.out.println("JSON Parser has parsed : " + text + ".json");
    }
}
