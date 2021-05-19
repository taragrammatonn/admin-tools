package flux.admintools.configuration;

import flux.admintools.handlers.AuthHandler;
import flux.admintools.handlers.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class GreetingRouter {

    private final AuthHandler authHandler;

    public GreetingRouter(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        return RouterFunctions
                .route(
                        GET("/"),
                        greetingHandler::index
                ).andRoute(
                        GET("/auth"), req ->
                                ServerResponse.temporaryRedirect(URI.create("/auth/login"))
                                .build());
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return RouterFunctions
                .route(
                        POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login
                )
                .andRoute(
                        POST("/auth/logout").and(accept(APPLICATION_JSON)), authHandler::logout
                );
    }
}
