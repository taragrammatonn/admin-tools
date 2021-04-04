package flux.admintools.handlers;

import flux.admintools.domen.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class GreetingHandler {

    @Value("${spring.profiles.active}")
    private String profile;

    public Mono<ServerResponse> hello(ServerRequest request) {
        Long start = request.queryParam("start")
                .map(Long::valueOf)
                .orElse(0L);
        Long count = request.queryParam("count")
                .map(Long::valueOf)
                .orElse(3L);

        Flux<Message> data = Flux
                .just(
                        "Hello, reactive!",
                        "More then one",
                        "Third post",
                        "Fourth post",
                        "Fifth post"
                )
                .skip(start)
                .take(count)
                .map(Message::new);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, Message.class);
    }

    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        String user = serverRequest.queryParam("user")
                .orElse("Nobody");
        Map<String, Object> model = new HashMap<>();

        model.put("user", user);
        model.put("isDevMode", profile.equals("dev"));

        return ServerResponse
                .ok()
                .render("index", model);
    }

    public Mono<ServerResponse> authorization(ServerRequest serverRequest) {
        Map<String, String> model = new HashMap<>();

        return ServerResponse
                .ok()
                .render("index", model);
    }
}

