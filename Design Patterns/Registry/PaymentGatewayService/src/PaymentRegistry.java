import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaymentRegistry {
    private static final Map<String, PaymentProvider> registry = new HashMap<>();

    static {
        loadProvidersFromAnnotations();
    }
    
    private static void loadProvidersFromAnnotations() {
        System.out.println("Auto-discovering payment providers using annotations...");
        
        try {
            List<Class<?>> annotatedClasses = findAnnotatedClasses("", PaymentProviderService.class);
            
            for (Class<?> clazz : annotatedClasses) {
                if (PaymentProvider.class.isAssignableFrom(clazz)) {
                    PaymentProvider provider = (PaymentProvider) clazz.getDeclaredConstructor().newInstance();
                    String providerName = getProviderName(clazz, provider);
                    registry.put(providerName.toLowerCase(), provider);
                    System.out.println("Registered provider: " + providerName);
                }
            }
            
            if (registry.isEmpty()) {
                System.out.println("No @PaymentProviderService annotated classes found.");
            }
            
        } catch (Exception e) {
            System.err.println("Error during provider discovery: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String getProviderName(Class<?> clazz, PaymentProvider provider) {
        PaymentProviderService annotation = clazz.getAnnotation(PaymentProviderService.class);
        if (annotation != null && !annotation.value().isEmpty()) {
            return annotation.value();
        }
        return provider.getName();
    }
    
    private static List<Class<?>> findAnnotatedClasses(String packageName, Class<PaymentProviderService> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            // Get the current working directory and scan the bin folder
            String classpath = System.getProperty("java.class.path");
            String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
            
            for (String entry : classpathEntries) {
                File file = new File(entry);
                if (file.isDirectory()) {
                    classes.addAll(findClassesInDirectory(file, ""));
                }
            }
        } catch (Exception e) {
            System.err.println("Error scanning classpath: " + e.getMessage());
        }
        
        List<Class<?>> annotatedClasses = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                annotatedClasses.add(clazz);
            }
        }
        
        return annotatedClasses;
    }
    
    private static List<Class<?>> findClassesInDirectory(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String subPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                    classes.addAll(findClassesInDirectory(file, subPackage));
                } else if (file.getName().endsWith(".class")) {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        String fullClassName = packageName.isEmpty() ? className : packageName + "." + className;
                        Class<?> clazz = Class.forName(fullClassName);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        // Ignore classes that can't be loaded
                    }
                }
            }
        }
        
        return classes;
    }

    public static void addRegistry(String provider, PaymentProvider providerClass) {
        registry.put(provider.toLowerCase(), providerClass);
    }

    public static PaymentProvider getPaymentProvider(String provider) {
        if (!registry.containsKey(provider.toLowerCase())) {
            throw new IllegalArgumentException("Illegal provider requested");
        }
        return registry.get(provider.toLowerCase());
    }
}
