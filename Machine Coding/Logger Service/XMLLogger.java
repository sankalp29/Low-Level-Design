public class XMLLogger implements Logger {

    public XMLLogger() {}

    @Override
    public String type() {
        return "xml";
    }

    @Override
    public String log(String message) {
        return ("<log> " + message + " </log>");
    }   
}
