package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model containing information about cart_items.
 *
 * @author TimvHal
 * @version 06-01-2020
 */
public class CartItem {
    public String itemId;
    public String articleId;
    public String email;
    public String name;
    public Double price;

    public CartItem(@JsonProperty("cart_item_id") String itemId, @JsonProperty("article_id") String articleId,
                    @JsonProperty("email") String email, @JsonProperty("name") String name,
                    @JsonProperty("price") Double price) {
        this.itemId = itemId;
        this.articleId = articleId;
        this.email = email;
        this.name = name;
        this.price = price;
    }
}
