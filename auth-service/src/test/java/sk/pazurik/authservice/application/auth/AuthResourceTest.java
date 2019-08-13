package sk.pazurik.authservice.application.auth;

import sk.pazurik.authservice.application.auth.AuthResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import sk.pazurik.authservice.domain.auth.AuthService;
import sk.pazurik.authservice.domain.auth.TestCredentials;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuthResourceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    AuthResource authResource;

    @Mock
    Logger logger;

    @Mock
    AuthService authService;

    @Test
    public void authenticateShouldReturn200() throws Exception {
        Mockito.when(authService.retrieveTokenForCredentials(TestCredentials.validCredentialDTO)).thenReturn(TestCredentials.UNLIMITED_TOKEN);
        Response response = authResource.login(TestCredentials.validCredentialDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getHeaderString(AUTHORIZATION)).isNotEmpty();
    }

    @Test
    public void authenticateShouldReturn401() throws Exception {
        thrown.expect(SecurityException.class);
        Mockito.when(authService.retrieveTokenForCredentials(TestCredentials.invalidCredentialDTO)).thenThrow(SecurityException.class);
        Response response = authResource.login(TestCredentials.invalidCredentialDTO);
        assertThat(response.getStatus()).isEqualTo(401);
    }
}