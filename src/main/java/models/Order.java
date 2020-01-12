package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.OrderStatus;

/**
 * Model Order contains information about an order made by a user.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class Order {

    private String orderId;
    private String articleId;
    private String email;
    private String name;
    private Double price;
    private OrderStatus orderStatus;
    private String dateBought;

    public Order(@JsonProperty("article_id") String articleId, @JsonProperty("email") String email,
                 @JsonProperty("name") String name, @JsonProperty("price") Double price,
                 @JsonProperty("order_status") String orderStatus) {
        this.orderId = orderId;
        this.articleId = articleId;
        this.email = email;
        this.name = name;
        this.price = price;
        this.orderStatus = OrderStatus.valueOf(orderStatus.toUpperCase());
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public String getOrderId() {
        return this.orderId;
    }
}
