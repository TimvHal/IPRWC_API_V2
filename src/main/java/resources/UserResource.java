package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import models.BasicAuth;
import models.RegistryForm;
import models.Response;
import models.User;
import org.jose4j.lang.JoseException;
import services.JacksonService;
import services.JsonWebTokenService;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;

/**
 * Resource class for User. Mainly for registering and loging in.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
@Path("/user")
public class UserResource implements JacksonService {

    private static ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response token(@Auth BasicAuth credentials) throws NoSuchAlgorithmException, JoseException {
        return new Response(JsonWebTokenService.buildToken(credentials.user).getCompactSerialization());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@Auth User user) {
        User[] userList = {user};
        return writeValueAsString(userList);
    }

    @POST
    @Path("/register")
    public boolean registerUser(String jsonString) {
        return UserService.registerUser(readValue(jsonString));
    }

    @Override
    public String writeValueAsString(Object[] userList) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public RegistryForm readValue(String jsonString) {
        RegistryForm form = null;
        try {
            form = mapper.readValue(jsonString, RegistryForm.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return form;
    }
}
