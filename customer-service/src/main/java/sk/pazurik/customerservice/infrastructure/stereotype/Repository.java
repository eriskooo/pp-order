package sk.pazurik.customerservice.infrastructure.stereotype;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.transaction.Transactional;

/**
 * Stereotype for a stateless component that provides database/datasource access.
 */
@Stereotype
@ApplicationScoped
@Retention(RUNTIME)
@Target(TYPE)
@Transactional
public @interface Repository {

}
