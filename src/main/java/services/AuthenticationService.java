package services;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import models.BasicAuth;
import models.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

/**
 * Service for authentication and password hashing.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class AuthenticationService implements Authenticator<BasicCredentials, BasicAuth> {
    private final static int MINIMUM_SALT_SIZE = 16;
    private final static int MAXIMUM_SALT_SIZE = 25;
    private static Base64.Encoder base64Encoder = Base64.getEncoder();
    private static Base64.Decoder base64Decoder = Base64.getDecoder();

    /**
     * Generate a random salt.
     *
     * @return random salt.
     */
    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] seed = secureRandom.generateSeed(20);
        secureRandom.setSeed(seed);
        final int SALT_SIZE =
                secureRandom.nextInt(MAXIMUM_SALT_SIZE - MINIMUM_SALT_SIZE + 1) + MINIMUM_SALT_SIZE;
        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);
        return base64Encoder.encodeToString(salt);
    }

    /**
     * Hashes a combination of password and salt with help from a SecretKeyFactory using the PBKDF2WithHmacSHA1-algorithm.
     *
     * @param encryptedSalt salt in base64.
     * @param password password provided by user.
     * @return hashed password
     */
    public static String applySaltAndHash(String encryptedSalt, String password) {
        byte[] hashedPassword = null;
        byte[] salt = base64Decoder.decode(encryptedSalt);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return base64Encoder.encodeToString(hashedPassword);
    }

    @Override
    public Optional<BasicAuth> authenticate(BasicCredentials credentials) {

        Optional<User> optionalUser = UserService.getUserByCredentials(credentials);

        if (!optionalUser.isPresent()) return Optional.empty(); // No user with matching email found

        User user = optionalUser.get();
        String saltedHash = applySaltAndHash(user.getSalt(), credentials.getPassword());

        if (!user.getPassword().equals(saltedHash)) { return Optional.empty(); } // Passwords don't match

        return Optional.of(new BasicAuth(user));

    }
}
