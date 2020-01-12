package models;

/**
 * Model containing the jwt token upon successfully log in.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class JWTResponse {

    public String jsonWebToken;

    public JWTResponse(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }
}
