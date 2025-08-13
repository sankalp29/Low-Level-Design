import java.util.HashMap;
import java.util.Map;

public class LoggerRegistry {
    private final static Map<String, Logger> loggerRegistry = new HashMap<>();

    public Logger getLogger(String type) {
        if (type == null) throw new IllegalArgumentException("Logger type cannot be null.");
        if (!loggerRegistry.containsKey(type)) throw new IllegalArgumentException("Illegal logger type requested");

        return loggerRegistry.get(type);
    }

    public void registerLogger(String type, Logger logger) {
        loggerRegistry.put(type, logger);
    }
}
