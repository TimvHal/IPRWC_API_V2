package services;

import DAO.OrderDAO;
import models.Order;
import models.User;

public class OrderService {

    public static Order[] getOrders(User user) {
        return OrderDAO.getOrders(user);
    }

    public static boolean postOrder(User user, String articleId) {
        return OrderDAO.postOrder(user, articleId);
    }

    public static boolean updateOrder(User user, String orderId) {
        return OrderDAO.updateOrder(user, orderId);
    }

    public static boolean deleteOrder(String orderId) {
        return OrderDAO.deleteOrder(orderId);
    }
}
