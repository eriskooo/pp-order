/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package sk.pazurik.customerservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.regex.Pattern;

import static sk.pazurik.customerservice.infrastructure.security.JwtClaims.CUSTOM_CLAIM_ROLES;

/**
 * JAX-RS Request Filter to intercept authorization header and verify the validity of the JWT.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthorizationFilter implements ContainerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private static final Pattern PATTERN_AUTHORIZATION_HEADER = Pattern.compile("^Bearer [a-zA-Z0-9\\-_\\.]+$", Pattern.CASE_INSENSITIVE);

    private String secretKey;
    private String issuer;

    public JwtAuthorizationFilter() {
        super();
    }

    @Inject
    public JwtAuthorizationFilter(
            @ConfigProperty(name = "jwt.public-key") final String secretKey,
            @ConfigProperty(name = "jwt.issuer") final String issuer) {
        this();
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        if (isMethodOptions(requestContext)) {
            requestContext.abortWith(Response.status(Status.OK).build());
            return;
        }

        if (isWhitelistedUrl(requestContext)) {
            return;
        }

        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            throw new NotAuthorizedException("Authorization header is missing", Response.status(Status.UNAUTHORIZED));
        }

        if (!isValidAuthorizationHeader(header)) {
            throw new NotAuthorizedException("Authorization header is invalid", Response.status(Status.UNAUTHORIZED));
        }

        try {
            String[] headerComponents = header.split(" ");
            String token = headerComponents[1];

            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer(issuer)
                    .acceptLeeway(1)
                    .acceptExpiresAt(5)
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            String[] roles = getRoles(jwt.getClaim(CUSTOM_CLAIM_ROLES).asString());

            JwtSecurityContext securityContext = new JwtSecurityContext(username, roles);
            requestContext.setSecurityContext(securityContext);

        } catch (JWTVerificationException e) {
            String message = "JWT token verification failed";
            LOG.error(message, e);
            throw new NotAuthorizedException(message, Response.status(Status.UNAUTHORIZED));
        }
    }

    private boolean isMethodOptions(final ContainerRequestContext requestContext) {
        return requestContext.getMethod().equals(HttpMethod.OPTIONS);
    }

    private boolean isWhitelistedUrl(final ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getAbsolutePath().getPath();

        //todo : set to false to make jwt stuff done.
        return true;
    }

    private boolean isValidAuthorizationHeader(final String header) {
        return PATTERN_AUTHORIZATION_HEADER.matcher(header).matches();
    }

    private String[] getRoles(final String s) {
        return s.replaceAll("(\\[|\\])", "").split(",");
    }
}
