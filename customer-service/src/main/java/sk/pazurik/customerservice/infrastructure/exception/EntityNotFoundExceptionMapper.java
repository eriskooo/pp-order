package sk.pazurik.customerservice.infrastructure.exception;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        ExceptionDTO error = new ExceptionDTO(404, exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new GenericEntity<ExceptionDTO>(error) {})
                .build();
    }
}

