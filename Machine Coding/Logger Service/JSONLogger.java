public class JSONLogger implements Logger {

    public JSONLogger() {}

    @Override
    public String type() {
        return "json";
    }

    @Override
    public String log(String message) {
        return ("{ log: " +" message : " + message + " }");
    }
}
