package flux.admintools.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.service.UserUnicastService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DefaultWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final UserUnicastService userUnicastService;

    public DefaultWebSocketHandler(ObjectMapper objectMapper, UserUnicastService userUnicastService) {
        this.objectMapper = objectMapper;
        this.userUnicastService = userUnicastService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> messages = session.receive()
                .flatMap(message -> userUnicastService.getMessages())
                .flatMap(o -> {
                    try {
                        return Mono.just(objectMapper.writeValueAsString(o));
                    } catch (JsonProcessingException e) {
                       return Mono.error(e);
                    }
                }).map(session::textMessage);
        return session.send(messages);
    }
}
