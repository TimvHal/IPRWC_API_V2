package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import models.Order;
import models.User;
import services.JacksonService;
import services.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;

@Path("/order")
public class OrderResource implements JacksonService {

    private static ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@Auth User user) {
        return writeValueAsString(OrderService.getOrders(user));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{article_id}")
    public boolean postOrder(@Auth User user, @PathParam("article_id") String articleId) {
        return OrderService.postOrder(user, articleId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{order_id}")
    public boolean updateOrder(@Auth User user, @PathParam("order_id") String orderId) {
        return OrderService.updateOrder(user, orderId);
    }

    @DELETE
    @Path("/{order_id}")
    public boolean deleteOrder(@Auth User user, @PathParam("order_id") String orderId) {
        return OrderService.deleteOrder(orderId);
    }

    @Override
    public String writeValueAsString(Object[] orderList) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(orderList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public Order readValue(String jsonString) {
        Order order = null;
        try {
            order = mapper.readValue(jsonString, Order.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
