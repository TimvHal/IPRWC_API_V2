package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Status;

import java.util.UUID;

/**
 * Model containing data for a car.
 *
 * @author TimvHal
 * @Version 03/01/2020
 */
public class Car extends Object {

    public UUID articleId;
    public String brand;
    public String model;
    public String description;
    public double price;
    public String engine;
    public int power;
    public int modelYear;
    public Status status;
    public boolean isSold;
    public String dateAdded;

    @JsonCreator()
    public Car(@JsonProperty("brand") String brand, @JsonProperty("model") String model,
               @JsonProperty("description") String description, @JsonProperty("price") double price,
               @JsonProperty("engine") String engine, @JsonProperty("power") int power,
               @JsonProperty("model_year") int modelYear, @JsonProperty("status") String status) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
        this.engine = engine;
        this.power = power;
        this.modelYear = modelYear;
        this.status = Status.valueOf(status.toUpperCase());
        this.isSold = false;
    }

    public void setArticleId(UUID articleId) {
        this.articleId = articleId;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
