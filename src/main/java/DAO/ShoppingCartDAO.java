package DAO;

import models.CartItem;
import models.User;
import services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ShoppingCartDAO {
    public static CartItem[] getCartItems(User user) {
        ArrayList<CartItem> cartItemList = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("SELECT * FROM cart_items WHERE user_email = ?;");
            ps.setString(1, user.getEmail());
            ResultSet rs = DatabaseService.executeQuery(ps);

            while(rs.next()) {
                String itemId = rs.getString("cart_item_id");
                String articleId = rs.getString("article_id");
                String userEmail = rs.getString("user_email");
                String name = "";
                Double price = 0.0;

                PreparedStatement ps1 = DatabaseService.prepareQuery("SELECT brand, model, price FROM cars WHERE article_id = ?;");
                ps1.setObject(1, UUID.fromString(articleId));
                ResultSet rs1 = DatabaseService.executeQuery(ps1);

                while(rs1.next()) {
                    name = rs1.getString("brand") + " " + rs1.getString("model");
                    price = rs1.getDouble("price");
                }

                CartItem current = new CartItem(articleId, userEmail, name, price);
                current.setItemId(itemId);
                cartItemList.add(current);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        CartItem[] cartItemArray = new CartItem[cartItemList.size()];
        for(int i = 0; i < cartItemList.size(); i++) {
            cartItemArray[i] = cartItemList.get(i);
        }
        return cartItemArray;
    }

    public static boolean postCartItem(User user, String articleId) {
        try {
            CartItem[] cartItems = getCartItems(user);
            for(CartItem item : cartItems) {
                if(item.getArticleId().equals(articleId)) { return false; } //Item already exists in users shopping cart.
            }

            PreparedStatement ps = DatabaseService.prepareQuery("INSERT INTO cart_items VALUES(?,?,?);");
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, articleId);
            ps.setString(3, user.getEmail());

            DatabaseService.executeUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCartItem(String cartItemId) {
        try {
            PreparedStatement ps = DatabaseService.prepareQuery("DELETE FROM cart_items WHERE cart_item_id = ?");
            ps.setString(1, cartItemId);
            DatabaseService.executeUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
