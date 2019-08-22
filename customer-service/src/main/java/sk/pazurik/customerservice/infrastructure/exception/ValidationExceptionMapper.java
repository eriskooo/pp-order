package sk.pazurik.customerservice.infrastructure.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collection;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException e) {
        Collection<ExceptionDTO> errors =
                e.getConstraintViolations()
                        .stream()
                        .map(err -> new ExceptionDTO(400, err.getMessage())).collect(Collectors.toList());


        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new GenericEntity<Collection<ExceptionDTO>>(errors) {
                })
                .build();
    }
}
