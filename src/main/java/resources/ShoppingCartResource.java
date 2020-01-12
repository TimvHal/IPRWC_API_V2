package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import models.Car;
import models.CartItem;
import models.User;
import services.CarService;
import services.JacksonService;
import services.ShoppingCartService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;

@Path("/shoppingcart")
public class ShoppingCartResource implements JacksonService {

    private static ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCartItems(@Auth User user) {
        return writeValueAsString(ShoppingCartService.getCartItems(user));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{article_id}")
    public boolean postCartItem(@Auth User user, @PathParam("article_id") String articleId) {
        return ShoppingCartService.postCartItem(user, articleId);
    }

    @DELETE
    @Path("/{cart_item_id}")
    public boolean deleteCartItem(@Auth User user, @PathParam("cart_item_id") String cartItemId) {
        return ShoppingCartService.deleteCartItem(cartItemId);
    }

    @Override
    public String writeValueAsString(Object[] itemList) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(itemList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public CartItem readValue(String jsonString) {
        CartItem item = null;
        try {
            item = mapper.readValue(jsonString, CartItem.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

}
