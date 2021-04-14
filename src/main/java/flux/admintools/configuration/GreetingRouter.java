package flux.admintools.configuration;

import flux.admintools.handlers.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        RequestPredicate hello =
                GET("/hello")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        return RouterFunctions
                .route(hello, greetingHandler::hello)
                .andRoute(
                        GET("/"),
                        greetingHandler::index
                ).andRoute(
                        GET("/login"),
                        greetingHandler::authorization
                ).andRoute(
                        GET("/auth"), req ->
                                ServerResponse.temporaryRedirect(URI.create("/auth/login"))
                                .build());
    }
}
