public class PlainTextLogger implements Logger {

    public PlainTextLogger() {}

    @Override
    public String type() {
        return "plain";
    }

    @Override
    public String log(String message) {
        return ("[Plain] : " + message);
    }   
}
