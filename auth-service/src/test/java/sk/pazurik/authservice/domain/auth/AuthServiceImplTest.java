package sk.pazurik.authservice.domain.auth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AuthServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AuthService authService = new AuthServiceImpl("secret", "http://localhost:8080/auth-service/api/v1/auth");

    @Test
    public void givenValidCredentialsWhenRetrieveTokenThenSuccess() {
        authService.retrieveTokenForCredentials(TestCredentials.validCredentialDTO);
    }

    @Test
    public void givenInValidCredentialsWhenRetrieveTokenThenSuccess() {
        thrown.expect(SecurityException.class);
        authService.retrieveTokenForCredentials(TestCredentials.invalidCredentialDTO);
    }
}
