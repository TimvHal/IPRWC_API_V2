package services;

import DAO.CarDAO;
import models.Car;

/**
 * Service class for car.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class CarService {

    public static Car[] getCars() {
        return CarDAO.getCars();
    }

    public static Car[] getCar(String articleId) {
       return CarDAO.getCar(articleId);
    }

    public static String postCar(Car car) {
        return CarDAO.postCar(car);
    }

    public static String updateCar(Car car) {
        return CarDAO.updateCar(car);
    }

    public static String deleteCar(String articleId) {
        return CarDAO.deleteCar(articleId);
    }
}
