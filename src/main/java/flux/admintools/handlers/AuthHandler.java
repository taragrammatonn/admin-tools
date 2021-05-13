package flux.admintools.handlers;

import com.devglan.springwebfluxjwt.dto.ApiResponse;
import flux.admintools.configuration.jwt.JWTUtil;
import flux.admintools.configuration.security.PBKDF2Encoder;
import flux.admintools.domen.authorization.AuthRequest;
import flux.admintools.domen.authorization.AuthResponse;
import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthHandler {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final PBKDF2Encoder passwordEncoder;

    @Autowired
    public AuthHandler(UserService userService, JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<AuthRequest> authRequest = request.bodyToMono(AuthRequest.class);
        return authRequest.flatMap(login ->
                userService.findByUsername(login.getUser())
                .cast(User.class)
                .flatMap(user -> {
                    if (login.getPassword().equals(user.getPassword())) {

                        return ServerResponse
                                .ok()
                                .contentType(APPLICATION_JSON)
                                .body(BodyInserters.fromValue(new AuthResponse(jwtUtil.generateToken(user))));
                    } else {
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "Invalid credentials", null)));
                    }
                }).switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "User does not exist", null)))));
    }
}
