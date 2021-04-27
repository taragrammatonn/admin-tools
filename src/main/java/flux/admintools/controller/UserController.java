package flux.admintools.controller;

import com.fasterxml.jackson.annotation.JsonView;
import flux.admintools.configuration.JWTUtil;
import flux.admintools.domen.users.User;
import flux.admintools.domen.users.Views;
import flux.admintools.service.UserService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    private final JWTUtil jwtUtil;

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
    @JsonView(Views.GeneralUserInfo.class)
    public Flux<User> list() {
        return Flux.from(userService.list());
    }

    @PostMapping
    public Mono<User> add(@RequestBody User user) {
        return userService.addOne(user);
    }

    @GetMapping("/user/{id}")
    public Mono<User> getById(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @SneakyThrows
    @GetMapping("/sessionUser")
    public ResponseEntity<String> getSessionUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jwtUtil.decodeToken(token));
    }
}
