package services;

import DAO.ShoppingCartDAO;
import models.CartItem;
import models.User;

public class ShoppingCartService {
    public static CartItem[] getCartItems(User user) {
        return ShoppingCartDAO.getCartItems(user);
    }

    public static boolean postCartItem(User user, String articleId) {
        return ShoppingCartDAO.postCartItem(user, articleId);
    }

    public static boolean deleteCartItem(String cartItemId) {
        return ShoppingCartDAO.deleteCartItem(cartItemId);
    }

    public static boolean emptyCart(String email) {
        return ShoppingCartDAO.emptyCart(email);
    }
}
