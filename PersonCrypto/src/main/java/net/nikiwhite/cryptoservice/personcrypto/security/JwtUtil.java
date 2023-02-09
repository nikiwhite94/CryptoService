package net.nikiwhite.cryptoservice.personcrypto.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    //todo keycloak

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final String SUBJECT = "Person details";
    private static final String CLAIM_EMAIL = "email";
    private static final String ISSUER = "Crypto Service";

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim(CLAIM_EMAIL, email)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(ZonedDateTime.now().plusMinutes(300).toInstant())
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT.getClaim(CLAIM_EMAIL).asString();
    }
}
