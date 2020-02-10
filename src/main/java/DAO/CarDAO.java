package DAO;

import models.Car;
import services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * DAO class for cars. It contains all CRUD-operations.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class CarDAO {
    public static Car[] getCars() {
        ArrayList<Car> carList = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("SELECT * FROM cars;");
            ResultSet rs = DatabaseService.executeQuery(ps);

            while(rs.next()) {
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String engine = rs.getString("engine");
                int power = rs.getInt("power");
                int modelYear = rs.getInt("model_year");
                String status = rs.getString("status");
                String imageUrl = rs.getString("image_url");

                Car current = new Car(brand, model, description, price, engine, power, modelYear, status, imageUrl);
                current.setArticleId(rs.getString("article_id"));
                current.setIsSold(rs.getBoolean("is_sold"));
                current.setDateAdded(rs.getDate("date_added").toString());
                carList.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Car[] carArray = new Car[carList.size()];
        for(int i = 0; i < carList.size(); i++) {
            carArray[i] = carList.get(i);
        }
        return carArray;
    }

    public static Car[] getCar(String articleId) {
        ArrayList<Car> carList = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("SELECT * FROM cars WHERE article_id = ?;");
            ps.setObject(1, UUID.fromString(articleId));
            ResultSet rs = DatabaseService.executeQuery(ps);

            while(rs.next()) {
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String engine = rs.getString("engine");
                int power = rs.getInt("power");
                int modelYear = rs.getInt("model_year");
                String status = rs.getString("status");
                String imageUrl = rs.getString("image_url");

                Car current = new Car(brand, model, description, price, engine, power, modelYear, status, imageUrl);
                current.setArticleId(rs.getString("article_id"));
                current.setIsSold(rs.getBoolean("is_sold"));
                current.setDateAdded(rs.getDate("date_added").toString());
                carList.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Car[] carArray = new Car[carList.size()];
        for(int i = 0; i < carList.size(); i++) {
            carArray[i] = carList.get(i);
        }
        return carArray;
    }

    public static String postCar(Car car) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("INSERT INTO cars VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getDescription());
            ps.setDouble(5, car.getPrice());
            ps.setString(6, car.getEngine());
            ps.setInt(7, car.getPower());
            ps.setInt(8, car.getModelYear());
            ps.setString(9, car.getStatus().toString().toLowerCase());
            ps.setBoolean(10, car.getIsSold());
            ps.setObject(11, timestamp);
            ps.setString(12, car.getImageUrl());

            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    public static String updateCar(Car car) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("UPDATE cars SET brand = ?, model = ?, description = ?," +
                    " price = ?, engine = ?, power = ?, model_year = ?, status = ?, is_sold = ?, image_url = ? WHERE article_id = ?;");
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getDescription());
            ps.setDouble(4, car.getPrice());
            ps.setString(5, car.getEngine());
            ps.setInt(6, car.getPower());
            ps.setInt(7, car.getModelYear());
            ps.setString(8, car.getStatus().toString().toLowerCase());
            ps.setBoolean(9, car.getIsSold());
            ps.setString(10, car.getImageUrl());
            ps.setObject(11, car.getArticleId());

            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    public static String deleteCar(String articleId) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("DELETE FROM cars WHERE article_id = ?;");
            ps.setObject(1, articleId);
            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
