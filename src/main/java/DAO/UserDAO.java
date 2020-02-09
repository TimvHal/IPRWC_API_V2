package DAO;

import io.dropwizard.auth.basic.BasicCredentials;
import models.User;
import services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO class for users. Mainly registration and login attempts are handled here.
 */
public class UserDAO {

    public static void registerUser(User user) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("INSERT INTO users VALUES(?,?,?,?,?,?);");
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getSalt());
            ps.setBoolean(6, user.getIsAdmin());

            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<User> getUserById(String id) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery(
                    "SELECT user_id, email, username, hashed_password, salt, is_admin FROM users " +
                            "WHERE user_id = ? ");

            ps.setObject(1, UUID.fromString(id));
            return getUser(ps);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<User> getUserByCredentials(BasicCredentials credentials) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery(
                    "SELECT user_id, email, username, hashed_password, salt, is_admin FROM users " +
                            "WHERE email = ?;");

            ps.setString(1, credentials.getUsername());
            return getUser(ps);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

        public static Optional<User> getUser(PreparedStatement ps) {
        try {
            ResultSet rs = DatabaseService.executeQuery(ps);

            String userId = "";
            String email = "";
            String name = "";
            boolean isAdmin = false;
            String password = "";
            String salt = "";

            int resultCount = 0;
            while(rs.next()) {
                resultCount++;

                userId = rs.getString("user_id");
                email = rs.getString("email");
                name = rs.getString("username");
                isAdmin = rs.getBoolean("is_admin");
                password = rs.getString("hashed_password");
                salt = rs.getString("salt");
            }

            if (resultCount == 1) {
                User user = new User(userId, email, name, isAdmin, password, salt);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
