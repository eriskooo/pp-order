package sk.pazurik.authservice.application.auth;

import org.slf4j.Logger;
import sk.pazurik.authservice.domain.auth.AuthService;
import sk.pazurik.authservice.domain.auth.CredentialDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("auth")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class AuthResource {

    @Inject
    private Logger logger;

    @Inject
    private AuthService authService;

    @POST
    public Response login(final @Valid CredentialDTO credentialDTO) {

        logger.info("called with valid {}", credentialDTO);

        String token = authService.retrieveTokenForCredentials(credentialDTO);

        return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
    }
}
