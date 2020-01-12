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

    private String articleId;
    private  String brand;
    private String model;
    private String description;
    private double price;
    private String engine;
    private int power;
    private int modelYear;
    private Status status;
    private boolean isSold;
    private String dateAdded;

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

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getArticleId() {
        return this.articleId;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEngine() {
        return this.engine;
    }

    public int getPower() {
        return this.power;
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean getIsSold() {
        return this.isSold;
    }
}
