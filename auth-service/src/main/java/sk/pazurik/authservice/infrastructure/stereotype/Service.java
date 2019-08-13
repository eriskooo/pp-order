package sk.pazurik.authservice.infrastructure.stereotype;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import javax.validation.executable.ValidateOnExecution;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Stereotype for a stateless component that contains business logic.
 */
@Stereotype
@ApplicationScoped
@ValidateOnExecution
@Retention(RUNTIME)
@Target(TYPE)
public @interface Service {

}
