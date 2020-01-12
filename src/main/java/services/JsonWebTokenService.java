package services;

import io.dropwizard.auth.Authenticator;
import models.User;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

public class JsonWebTokenService implements Authenticator<JwtContext, User> {

    private static byte[] secretKey;

    @Override
    public Optional<User> authenticate(JwtContext jwtContext) {
        try {
            JwtClaims claims = jwtContext.getJwtClaims();

            String userId = (String) claims.getClaimValue("userId");

            return UserService.getUserById(userId);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static JsonWebSignature buildToken(User user) throws NoSuchAlgorithmException {

        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("userId", user.getUserId());
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature signature = new JsonWebSignature();
        signature.setPayload(claims.toJson());
        signature.setAlgorithmHeaderValue(HMAC_SHA256);
        signature.setKey(new HmacKey(getSecretKey()));
        return signature;
    }

    public static byte[] getSecretKey() throws NoSuchAlgorithmException {
        if (secretKey != null) return secretKey;

        secretKey = new byte[32];
        SecureRandom.getInstanceStrong().nextBytes(secretKey);
        return secretKey;
    }
}
