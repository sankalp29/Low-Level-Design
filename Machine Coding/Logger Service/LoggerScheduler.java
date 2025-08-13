import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoggerScheduler {
    private static LoggerRegistry loggerRegistry;
    private static LoggerScheduler INSTANCE;
    private final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean isFlushThreadRunning = new AtomicBoolean(false);

    public static LoggerScheduler getInstance(LoggerRegistry loggerRegistry) {
        if (INSTANCE == null) {
            synchronized (LoggerScheduler.class) {
                if (INSTANCE == null) INSTANCE = new LoggerScheduler(loggerRegistry);
            }
        }
        return INSTANCE;
    }

    private LoggerScheduler(LoggerRegistry loggerRegistry) {
        LoggerScheduler.loggerRegistry = loggerRegistry;
        loadLoggersFromConfig();
    }

    private void loadLoggersFromConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("META-INF/services/logger-config.properties")) {
            Properties properties = new Properties();

            if (input != null) {
                properties.load(input);

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    String key = entry.getKey().toString();
                    String className = entry.getValue().toString();
                    Logger logger = (Logger) Class.forName(className).getDeclaredConstructor().newInstance();

                    loggerRegistry.registerLogger(key, logger);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void startFlushThreadIfNeeded() {
        if (isFlushThreadRunning.compareAndSet(false, true)) {
            // Start the thread to flush the queue to disk
            Thread flushToDiskThread = new Thread(this::processLogs);
            flushToDiskThread.setDaemon(false);
            flushToDiskThread.start();
        }
    }

    private void processLogs() {
        while (true) { 
            try {
                String log = blockingQueue.poll(5, TimeUnit.SECONDS);
                if (log == null) break;
                System.out.println("[Writing to file] " + log);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                isFlushThreadRunning.set(false);
                if (!blockingQueue.isEmpty()) startFlushThreadIfNeeded();
            }
        }
    }

    public void log(String type, String message) {
        Logger logger = loggerRegistry.getLogger(type);
        if (logger == null) throw new IllegalArgumentException("Illegal logger type chosen...");
        blockingQueue.offer(logger.log(message));
        startFlushThreadIfNeeded();
    }
}