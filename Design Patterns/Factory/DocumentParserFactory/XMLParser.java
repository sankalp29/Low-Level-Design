package DocumentParserFactory;

public class XMLParser implements DocumentParser {

    @Override
    public void parse(String text) {
        System.out.println("XML Parser has parsed : " + text + ".xml");
    }
}
