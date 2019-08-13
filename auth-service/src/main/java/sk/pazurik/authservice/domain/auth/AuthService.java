package sk.pazurik.authservice.domain.auth;


public interface AuthService {

    /**
     * Retrieve token for authenticated user.
     *
     * @param credentialDTO
     * @return
     * @throws SecurityException
     */
    String retrieveTokenForCredentials(final CredentialDTO credentialDTO) throws SecurityException;
}
