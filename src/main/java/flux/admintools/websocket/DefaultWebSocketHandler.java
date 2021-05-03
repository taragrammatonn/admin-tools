package flux.admintools.websocket;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.domen.Views;
import flux.admintools.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class DefaultWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public DefaultWebSocketHandler(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @NonNull
    @Override
    @JsonView(Views.GeneralUserInfo.class)
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> users = session.receive()
                .flatMap(message -> userService.getUsers())
                .flatMap(o -> {
                    try {
                        return Mono.just(objectMapper.writeValueAsString(o));
                    } catch (JsonProcessingException e) {
                       return Mono.error(e);
                    }
                }).map(session::textMessage)
                .log();
        return session.send(users);
    }
}
