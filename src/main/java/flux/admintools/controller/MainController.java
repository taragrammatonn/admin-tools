package flux.admintools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.configuration.JWTUtil;
import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class MainController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final ObjectMapper mapper;

    private static final ResponseEntity<Object> FORBIDDEN = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    private static final ResponseEntity<Object> NO_CONTENT = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    @Autowired
    public MainController(UserService userService, JWTUtil jwtUtil, ObjectMapper mapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe, WebSession session) {
        return swe.getFormData().flatMap(credentials -> {
                    Mono<UserDetails> authUser = userService.findByUsername(credentials.getFirst("username"));

                    return authUser
                            .cast(User.class)
                            .map(userDetails -> {
                                        String token = jwtUtil.generateToken(userDetails);
                                        session.getAttributes().put("jwt", token);
                                        return Objects.equals(
                                                credentials.getFirst("password"),
                                                userDetails.getPassword()
                                        ) ? ResponseEntity.ok(token)
                                                : FORBIDDEN;
                                    }
                            ).defaultIfEmpty(FORBIDDEN);
                }
        );
    }

    @GetMapping("/sessionUser")
    public Mono<ResponseEntity> getSession(
            WebSession session,
            @RequestParam String username) {
        Mono<UserDetails> sessionUser = userService.findByUsername(username);

        if (sessionUser != null) {
            session.getAttributes().put("sessionUser", sessionUser.cast(User.class));

            return sessionUser.map(q ->
                    ResponseEntity.ok(new MappingJacksonValue(q))
            );
        }

        return Mono.just(ResponseEntity.ok(NO_CONTENT));
    }
}
