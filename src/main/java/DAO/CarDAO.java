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

                Car current = new Car(brand, model, description, price, engine, power, modelYear, status);
                current.setArticleId(UUID.fromString(rs.getString("article_id")));
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

    public static void postCar(Car car) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("INSERT INTO cars VALUES(?,?,?,?,?,?,?,?,?,?,?);");
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, car.brand);
            ps.setString(3, car.model);
            ps.setString(4, car.description);
            ps.setDouble(5, car.price);
            ps.setString(6, car.engine);
            ps.setInt(7, car.power);
            ps.setInt(8, car.modelYear);
            ps.setString(9, car.status.toString().toLowerCase());
            ps.setBoolean(10, car.isSold);
            ps.setObject(11, timestamp);

            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCar(Car car) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("UPDATE cars SET brand = ?, model = ?, description = ?," +
                    " price = ?, engine = ?, power = ?, model_year = ?, status = ?, is_sold = ? WHERE article_id = ?;");
            ps.setString(1, car.brand);
            ps.setString(2, car.model);
            ps.setString(3, car.description);
            ps.setDouble(4, car.price);
            ps.setString(5, car.engine);
            ps.setInt(6, car.power);
            ps.setInt(7, car.modelYear);
            ps.setString(8, car.status.toString().toLowerCase());
            ps.setBoolean(9, car.isSold);
            ps.setObject(10, car.articleId);

            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCar(String articleId) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("DELETE FROM cars WHERE article_id = ?;");
            ps.setObject(1, articleId);
            DatabaseService.executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
