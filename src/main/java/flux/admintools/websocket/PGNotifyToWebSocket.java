package flux.admintools.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.domen.conf.Configuration;
import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import io.reactiverse.pgclient.PgClient;
import io.reactiverse.pgclient.PgConnection;
import io.reactiverse.pgclient.PgPool;
import io.reactiverse.pgclient.PgPoolOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PGNotifyToWebSocket {

    private final Configuration configuration;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public PGNotifyToWebSocket(Configuration configuration, UserService userService, ObjectMapper objectMapper) {
        this.configuration = configuration;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public PgPool subscribedNotificationHandler() {
        PgPool client = pgPool();
        client.getConnection(asyncResult -> {
            if (asyncResult.succeeded()) {
                PgConnection connection = asyncResult.result();
                connection.notificationHandler(notification -> {
                    try {
                        var entityId = objectMapper.readTree(notification.getPayload()).get("id").asLong();
                        Mono<User> user = userService.getOne(entityId);

                        var dbAction = DBAction.valueOf(objectMapper.readTree(notification.getPayload()).get("action").asText());
                        var action = !dbAction.equals(DBAction.INSERT) && !dbAction.equals(DBAction.UPDATE);

                        if (action) user = Mono.just(new User(entityId, Boolean.TRUE));

                        userService.onNext(user.block());
                    } catch (JsonProcessingException ex) {
                        log.error("Cannot unparsed data!", ex);
                    }
                });
                connection.query("LISTEN " + TriggerChanel.user_action, ar -> log.info("Subscribed to channel"));
            } else log.error("PgPool subscriber was not able to connect to DB. Error: {}", asyncResult);
        });
        return client;
    }

    private PgPool pgPool() {
        PgPoolOptions options = new PgPoolOptions()
                .setPort(5432)
                .setHost(Configuration.DB_HOST)
                .setDatabase("timetable")
                .setUser(configuration.getDbUserName())
                .setPassword(configuration.getDbPassword())
                .setMaxSize(5);

        // Create the client pool
        return PgClient.pool(options);
    }
}
