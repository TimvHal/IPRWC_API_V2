package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model Order contains information about an order made by a user.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class Order {

    public String orderId;
    public String articleId;
    public String email;
    public String name;
    public Double price;
    public boolean orderStatus;

    public Order(@JsonProperty("order_id") String orderId, @JsonProperty("article_id") String articleId,
                 @JsonProperty("email") String email, @JsonProperty("name") String name,
                 @JsonProperty("price") Double price, @JsonProperty("order_status") boolean orderStatus) {
        this.orderId = orderId;
        this.articleId = articleId;
        this.email = email;
        this.name = name;
        this.price = price;
        this.orderStatus = orderStatus;
    }
}
