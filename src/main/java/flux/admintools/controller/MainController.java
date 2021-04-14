package flux.admintools.controller;

import flux.admintools.configuration.JWTUtil;
import flux.admintools.configuration.PBKDF2Encoder;
import flux.admintools.domen.authorization.AuthRequest;
import flux.admintools.domen.authorization.AuthResponse;
import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MainController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final PBKDF2Encoder passwordEncoder;

    private static final ResponseEntity<Object> FORBIDDEN = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    private static final ResponseEntity<Object> NO_CONTENT = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    @Autowired
    public MainController(UserService userService, JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar, WebSession session) {

        return userService.findByUsername(ar.getUsername())
                .cast(User.class)
                .map(userDetails -> {
            if (ar.getPassword().equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
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
