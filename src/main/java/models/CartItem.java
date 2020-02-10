package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model containing information about cart_items.
 *
 * @author TimvHal
 * @version 06-01-2020
 */
public class CartItem {
    private String cartItemId;
    private String articleId;
    private String email;
    private String name;
    private Double price;

    public CartItem(@JsonProperty("cart_item_id") String cartItemId, @JsonProperty("article_id") String articleId,
                    @JsonProperty("email") String email, @JsonProperty("name") String name,
                    @JsonProperty("price") Double price) {
        this.cartItemId = cartItemId;
        this.articleId = articleId;
        this.email = email;
        this.name = name;
        this.price = price;
    }

    public void setItemId(String itemId) {
        this.cartItemId = itemId;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public String getUserEmail() {
        return this.email;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getCartItemId() {
        return this.cartItemId;
    }
}
