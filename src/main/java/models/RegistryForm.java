package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model RegistryForm contains all data the user filled in upon account creation.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class RegistryForm {

    public String email;
    public String username;
    public String password;

    @JsonCreator
    public RegistryForm(@JsonProperty("email") String email, @JsonProperty("username") String username,
                        @JsonProperty("password") String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
