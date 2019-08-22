package sk.pazurik.customerservice.infrastructure.exception;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityExceptionMapper implements ExceptionMapper<SecurityException> {
    @Override
    public Response toResponse(SecurityException exception) {
        ExceptionDTO error = new ExceptionDTO(401, exception.getMessage());
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new GenericEntity<ExceptionDTO>(error) {})
                .build();
    }    
}
