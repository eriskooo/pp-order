package sk.pazurik.customerservice.infrastructure.stereotype;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.annotation.sql.DataSourceDefinition;

/**
 * Stereotype for a stateless component that provides database/datasource access.
 */
@Stereotype
@ApplicationScoped
@Retention(RUNTIME)
@Target(TYPE)
@DataSourceDefinition(
        name = "java:app/pporder/MyDS",
        className = "org.apache.derby.jdbc.ClientDriver",
        url = "jdbc:derby://localhost:1527/pporder",
        user = "appuser",
        password = "password")
public @interface Repository {

}
