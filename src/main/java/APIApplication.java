import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.Application;
import io.dropwizard.auth.*;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import models.BasicAuth;
import models.User;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import resources.CarResource;
import resources.ShoppingCartResource;
import resources.UserResource;
import services.AuthenticationService;
import services.JsonWebTokenService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;

/**
 * Main class starts Application.
 *
 * @author TimvHal
 * @version 07-01-2020
 */
public class APIApplication extends Application<APIConfiguration> {
    public static void main(final String[] args) throws Exception {
        new APIApplication().run(args);
    }

    @Override
    public String getName() {
        return "IPRWC_API";
    }

    @Override
    public void initialize(final Bootstrap<APIConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new ResourceConfigurationSourceProvider());
    }

    @Override
    public void run(final APIConfiguration configuration, final Environment environment) throws NoSuchAlgorithmException {

        // Registreer de controllers zodat DropWizard weet welke klasse de API calls verwerken.
        // We gebruiken een bulkRegister() zodat we niet knikker vaak environment.jersey().register() hoeven te callen.
        bulkRegister(environment,
                new CarResource(),
                new UserResource(),
                new ShoppingCartResource()
        );

        // Authenticator
        AuthenticationService basicAuthenticationService = new AuthenticationService();
        BasicCredentialAuthFilter<BasicAuth> basicFilter = new BasicCredentialAuthFilter.Builder<BasicAuth>().setAuthenticator(basicAuthenticationService).setPrefix("Basic").buildAuthFilter();

        JsonWebTokenService jwtAuthenticationService = new JsonWebTokenService();
        final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
                .setVerificationKey(new HmacKey(JsonWebTokenService.getSecretKey())).build();
        AuthFilter<JwtContext, User> jwtFilter = new JwtAuthFilter.Builder<User>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
                .setAuthenticator(jwtAuthenticationService).buildAuthFilter();

        final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(BasicAuth.class, basicFilter, User.class, jwtFilter));
        final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(BasicAuth.class, User.class));

        environment.jersey().register(feature);
        environment.jersey().register(binder);
        environment.jersey().register(RolesAllowedDynamicFeature.class);;

        // CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    //Register all everything in one go.
    private static void bulkRegister(Environment environment, Object ... resources) {
        for (Object resource : resources) {
            environment.jersey().register(resource);
        }

    }
}
