package flux.admintools.handlers;

import flux.admintools.configuration.jwt.JWTUtil;
import flux.admintools.domen.authorization.AuthRequest;
import flux.admintools.domen.authorization.AuthResponse;
import flux.admintools.domen.dto.ApiResponse;
import flux.admintools.domen.users.User;
import flux.admintools.service.ActivityService;
import flux.admintools.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class AuthHandler {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final ActivityService activityService;

    @Autowired
    public AuthHandler(UserService userService, JWTUtil jwtUtil, ActivityService activityService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.activityService = activityService;
    }

    @NonNull
    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<AuthRequest> authRequest = request.bodyToMono(AuthRequest.class);
        return authRequest.flatMap(login ->
                userService.findByUsername(login.getUser())
                        .cast(User.class)
                        .flatMap(user -> {
                            if (login.getPassword().equals(user.getPassword())) {
                                return activityService.loginUser(user)
                                        .then(
                                                ServerResponse
                                                        .ok()
                                                        .contentType(APPLICATION_JSON)
                                                        .body(BodyInserters.fromValue(new AuthResponse(jwtUtil.generateToken(user))))
                                        );
                            } else {
                                return ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "Invalid credentials", null)));
                            }
                        }).switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "User does not exist", null)))));
    }

    @NonNull
    public Mono<ServerResponse> logout(ServerRequest request) {
        Mono<AuthRequest> logoutRequest = request.bodyToMono(AuthRequest.class);

        return logoutRequest.flatMap(logout ->
                userService.findByUsername(logout.getUser())
                        .cast(User.class)
                        .flatMap(activityService::logoutUser)
                        .then(
                                ServerResponse
                                        .ok()
                                        .contentType(APPLICATION_JSON).build()
                        ));
    }
}
