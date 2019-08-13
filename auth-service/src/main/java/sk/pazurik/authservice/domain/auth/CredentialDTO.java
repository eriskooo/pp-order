package sk.pazurik.authservice.domain.auth;

import sk.pazurik.authservice.infrastructure.value.AbstractValueObject;

import javax.validation.constraints.NotEmpty;

/**
 * A DTO that represents the credentials for the authentication.
 */
public class CredentialDTO extends AbstractValueObject {

    @NotEmpty(message = "username must not be empty")
    private String username;

    @NotEmpty(message = "password must not be empty")
    private String password;

    public CredentialDTO() {
        super();
    }

    // test purposes
    CredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Object[] values() {
        return new Object[]{username, password};
    }

    @Override
    public String toString() {
        return "CredentialDTO{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
