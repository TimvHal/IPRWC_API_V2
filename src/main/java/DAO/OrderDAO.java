package DAO;

import enums.OrderStatus;
import models.CartItem;
import models.Order;
import models.User;
import services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class OrderDAO {
    public static Order[] getOrders(User user) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            PreparedStatement firstStatement = DatabaseService.prepareQuery("SELECT * FROM orders WHERE user_email = ?;");
            firstStatement.setString(1, user.getEmail());
            ResultSet firstResultSet = DatabaseService.executeQuery(firstStatement);

            while(firstResultSet.next()) {
                String orderId = firstResultSet.getString("order_id");
                String articleId = firstResultSet.getString("article_id");
                String userEmail = firstResultSet.getString("user_email");
                String orderStatus = firstResultSet.getString("order_status");
                String dateBought = firstResultSet.getString("date_bought");
                String name = "";
                Double price = 0.0;

                PreparedStatement secondStatement = DatabaseService.prepareQuery("SELECT brand, model, price FROM cars WHERE article_id = ?;");
                secondStatement.setObject(1, UUID.fromString(articleId));
                ResultSet secondResultSet = DatabaseService.executeQuery(secondStatement);

                while(secondResultSet.next()) {
                    name = secondResultSet.getString("brand") + " " + secondResultSet.getString("model");
                    price = secondResultSet.getDouble("price");
                }

                Order current = new Order(articleId, userEmail, name, price, orderStatus);
                current.setOrderId(orderId);
                current.setDateBought(dateBought);
                current.setOrderId(orderId);
                orderList.add(current);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        Order[] orderArray = new Order[orderList.size()];
        for(int i = 0; i < orderList.size(); i++) {
            orderArray[i] = orderList.get(i);
        }
        return orderArray;
    }

    public static boolean postOrder(User user, String articleId) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("INSERT INTO orders VALUES(?,?,?,?,?);");
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, articleId);
            ps.setString(3, user.getEmail());
            ps.setObject(4, OrderStatus.PROCESSING.toString().toLowerCase());
            ps.setObject(5, timestamp);

            DatabaseService.executeUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrder(User user, String orderId) {
        try {
            String orderStatus = "processing";
            Order[] orders = getOrders(user);
            for(Order order: orders) {
                if(order.getOrderId().equals(orderId)) {
                    if(order.getOrderStatus().toString().toLowerCase().equals("processing")) {
                        orderStatus = "finished"; //Order has finished.
                        break;
                    }
                    break;
                }
            }


            PreparedStatement ps = DatabaseService.prepareQuery("UPDATE orders SET order_status = ? WHERE order_id = ?;");
            ps.setString(1, orderStatus);
            ps.setString(2, orderId);

            DatabaseService.executeUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(String orderId) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("DELETE FROM orders WHERE order_id = ?");
            ps.setString(1, orderId);
            DatabaseService.executeUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
