package flux.admintools.handlers;

import flux.admintools.domen.conf.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class GreetingHandler {

    private final Configuration configuration;

    public GreetingHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @NonNull
    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        String user = serverRequest.queryParam("user")
                .orElse("Nobody");
        Map<String, Object> model = new HashMap<>();

        model.put("user", user);
        model.put("isDevMode", configuration.getProfile());

        return ServerResponse
                .ok()
                .render(configuration.indexRender(), model);
    }
}

