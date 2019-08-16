package sk.pazurik.customerservice.infrastructure.security;

/**
 * Predefined & custom JWT Claims
 *
 * @url https://www.iana.org/assignments/jwt/jwt.xhtml
 */
public final class JwtClaims {

  public static final String OPEN_ID_STANDARD_CLAIM_FIRSTNAME = "given_name";

  public static final String OPEN_ID_STANDARD_CLAIM_LASTNAME = "family_name";

  public static final String CUSTOM_CLAIM_ROLES = "roles";

  private JwtClaims() {
    super();
  }
}
