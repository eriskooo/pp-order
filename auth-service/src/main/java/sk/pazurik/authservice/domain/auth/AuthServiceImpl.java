package sk.pazurik.authservice.domain.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import sk.pazurik.authservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static sk.pazurik.authservice.infrastructure.security.JwtClaims.CUSTOM_CLAIM_ROLES;
import static sk.pazurik.authservice.infrastructure.security.JwtClaims.OPEN_ID_STANDARD_CLAIM_FIRSTNAME;
import static sk.pazurik.authservice.infrastructure.security.JwtClaims.OPEN_ID_STANDARD_CLAIM_LASTNAME;

@Service
public class AuthServiceImpl implements AuthService {

    final String secretKey;
    final String issuer;

    @Inject
    public AuthServiceImpl(@ConfigProperty(name = "jwt.public-key") final String secretKey,
                           @ConfigProperty(name = "jwt.issuer") final String issuer) {
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    @Override
    public String retrieveTokenForCredentials(final CredentialDTO credentialDTO) throws SecurityException {
        if (!credentialDTO.getPassword().equals(credentialDTO.getUsername())) {
            throw new SecurityException("user and password are not identical");
        }
        try {
            return getToken(credentialDTO);
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e.getMessage());
        }
    }

    private String getToken(final CredentialDTO credentialDTO) throws UnsupportedEncodingException {
        ZonedDateTime now = ZonedDateTime.now();
        Date issuedAt = toDate(now);
        Date expiresAt = toDate(now.plus(10, ChronoUnit.MINUTES));
        Date notBefore = toDate(now);

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withNotBefore(notBefore)
                .withSubject(credentialDTO.getUsername())
                .withClaim(OPEN_ID_STANDARD_CLAIM_FIRSTNAME, credentialDTO.getUsername())
                .withClaim(OPEN_ID_STANDARD_CLAIM_LASTNAME, credentialDTO.getUsername())
                .withClaim(CUSTOM_CLAIM_ROLES, "admin")
                .sign(Algorithm.HMAC256(secretKey));
    }

    private Date toDate(final ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}
