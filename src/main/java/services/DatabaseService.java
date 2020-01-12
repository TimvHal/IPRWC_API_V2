package services;

import java.sql.*;

/**
 * Service class responsible for all queries that will be send to the database.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class DatabaseService {
    private static String url = "jdbc:sqlite:C:/Users/Tim van Hal/Documents/HBO Leiden/Jaar 2/IPRWC/Database/iprwc_webshop.db";

    private static Connection con;
    {
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static PreparedStatement prepareQuery(String query) {
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(url);
            ps = con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static ResultSet executeQuery(PreparedStatement ps) {
        ResultSet rs = null;
        try {
            ps.execute();
            rs = ps.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public static void executeUpdate(PreparedStatement ps) {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
