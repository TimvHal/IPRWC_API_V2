package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import models.Car;
import models.User;
import services.CarService;
import services.JacksonService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Resource class for retrieving and posting cars.
 *
 * @author TimvHal
 * @version 03/01/2019
 */
@Path("/car")
public class CarResource implements JacksonService {

    private static ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCars() {
        return writeValueAsString(CarService.getCars());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{article_id}")
    public String getCar(@PathParam("article_id") String articleId) {
        return writeValueAsString(CarService.getCar(articleId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String postCar(@Auth User user, String jsonString) {
        if(!user.getIsAdmin()) {
            return "Not allowed: this user is not an admin.";
        }
        return CarService.postCar(readValue(jsonString));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{article_id}")
    public String updateCar(@Auth User user, String jsonString, @PathParam("article_id") String articleId) {
        if(!user.getIsAdmin()) {
            return "Not allowed: this user is not an admin.";
        }
        Car car = readValue(jsonString);
        car.setArticleId(articleId);
        return CarService.updateCar(car);
    }

    @DELETE
    @Path("/{article_id}")
    public String deleteCar(@Auth User user, @PathParam("article_id") String articleId) {
        if(!user.getIsAdmin()) {
            return "Not allowed: this user is not an admin.";
        }
        return CarService.deleteCar(articleId);
    }

    @Override
    public String writeValueAsString(Object[] carList) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(carList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public Car readValue(String jsonString) {
        Car car = null;
        try {
            car = mapper.readValue(jsonString, Car.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

    public ArrayList<Car> convertList(ArrayList<Object> list) {
        ArrayList<Car> carList = new ArrayList<>();
        for (Object object : list) {
            carList.add((Car) object);
        }
        return carList;
    }
}
