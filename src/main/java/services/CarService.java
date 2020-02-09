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

    public static void postCar(Car car) {
        CarDAO.postCar(car);
    }

    public static void updateCar(Car car) {
        CarDAO.updateCar(car);
    }

    public static void deleteCar(String articleId) {
        CarDAO.deleteCar(articleId);
    }
}
