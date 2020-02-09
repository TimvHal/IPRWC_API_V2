package services;

import DAO.UserDAO;
import io.dropwizard.auth.basic.BasicCredentials;
import models.RegistryForm;
import models.User;

import java.util.Optional;

/**
 * Service class for users.
 * An existing RegistryForm is taken and is, along with a newly generated salt, forged into a new user.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class UserService {

    public static boolean registerUser(RegistryForm registryForm) {

        if(registryForm.name.length() < 5 || registryForm.password.length() < 8 ||
            !registryForm.email.contains("@")) {
            return false;
        }
        User newUser = new User(registryForm);
        newUser.setSalt(AuthenticationService.generateSalt());
        newUser.setPassword(AuthenticationService.applySaltAndHash(newUser.getSalt(), newUser.getPassword()));
        UserDAO.registerUser(newUser);
        return true;
    }

    public static Optional<User> getUserById(String userId) {
        return UserDAO.getUserById(userId);
    }

    public static Optional<User> getUserByCredentials(BasicCredentials credentials) {
        return UserDAO.getUserByCredentials(credentials);
    }
}
