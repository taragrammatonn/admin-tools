package flux.admintools.websocket;

import flux.admintools.domen.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
public class WebSocketConfig {

    private static final String USER_FETCH = "/push";

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public WebSocketConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = Map.of(USER_FETCH, webSocketHandler);
        return new SimpleUrlHandlerMapping(map, -1);
    }

    @Bean
    public HandlerAdapter wsHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    Flux<User> users(Sinks.Many<User> publisher) {
        return publisher.asFlux();
    }

    @Bean
    Sinks.Many<User> publisher() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
