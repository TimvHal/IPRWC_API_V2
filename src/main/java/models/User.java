package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.Principal;

/**
 * Model containing information about users. Passwords are salted and hashed. The salt is encrypted.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class User implements Principal {

    private String userId;
    private String email;
    private String username;
    private boolean isAdmin;
    private String password;
    private String salt;

    public User(RegistryForm registryData) {
        this.email = registryData.email;
        this.username = registryData.username;
        this.isAdmin = false;
        this.password = registryData.password;
    }

    @JsonCreator
    public User(@JsonProperty("userId") String userId, @JsonProperty("email") String email,
                @JsonProperty("username") String username, @JsonProperty("isAdmin") boolean isAdmin,
                @JsonProperty("password") String password, @JsonProperty("salt") String salt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
        this.password = password;
        this.salt = salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public String getUserId() {
        return this.userId;
    }
}
