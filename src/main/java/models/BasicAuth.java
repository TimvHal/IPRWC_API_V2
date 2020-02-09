package models;

import java.security.Principal;

/**
 * Model LoginForm contains information about a login attempt.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class BasicAuth implements Principal {
    public User user;

    public BasicAuth(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

}
