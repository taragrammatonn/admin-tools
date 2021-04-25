package flux.admintools.controller;

import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
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
        Base64.Decoder decoder = Base64.getDecoder();
        String[] chunks = token.substring(7).split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(payload.replace("sub", "user"));
    }
}
