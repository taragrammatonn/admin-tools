package flux.admintools.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.domen.conf.Configuration;
import flux.admintools.domen.users.User;
import flux.admintools.repo.UserRepo;
import flux.admintools.service.UserUnicastService;
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
    private final UserUnicastService userUnicastService;
    private final ObjectMapper objectMapper;
    private final UserRepo userRepo;

    public PGNotifyToWebSocket(Configuration configuration, UserUnicastService userUnicastService, ObjectMapper objectMapper, UserRepo userRepo) {
        this.configuration = configuration;
        this.userUnicastService = userUnicastService;
        this.objectMapper = objectMapper;
        this.userRepo = userRepo;
    }

    @Bean
    public PgPool subscribedNotificationHandler() {
        PgPool client = pgPool();
        client.getConnection(asyncResult -> {
            if (asyncResult.succeeded()) {
                PgConnection connection = asyncResult.result();
                connection.notificationHandler(notification -> {
                    Mono<User> user;
                    try {
                        user = userRepo.findById(objectMapper.readTree(notification.getPayload()).get("id").asLong());

//                        switch (DBAction.valueOf(objectMapper.readTree(notification.getPayload()).get("action").asText())) {
//                            case INSERT, UPDATE -> userUnicastService.onNext(user.block());
//                            case DELETE -> {
//                                user.block().setIsDeleted(Boolean.TRUE);
//                                userUnicastService.onNext(user.block());
//                            }
//                        }
                        userUnicastService.onNext(user.block());
                    } catch (JsonProcessingException ex) {
                        log.error("Cannot unparsed data!", ex);
                    }
                });
                connection.query("LISTEN my_trigger_name", ar -> log.info("Subscribed to channel"));
            }
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
