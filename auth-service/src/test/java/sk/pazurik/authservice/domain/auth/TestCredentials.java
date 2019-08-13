package sk.pazurik.authservice.domain.auth;

public class TestCredentials {

    public static CredentialDTO validCredentialDTO = new CredentialDTO("astar", "astar");
    public static CredentialDTO invalidCredentialDTO = new CredentialDTO("astar", "seran");

    public static String UNLIMITED_TOKEN = "token";
}
