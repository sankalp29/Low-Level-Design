import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a Payment Provider Service.
 * Classes annotated with this will be automatically discovered and registered.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentProviderService {
    /**
     * The name of the payment provider. If not specified, uses the class name.
     */
    String value() default "";
}
