package flux.admintools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Comparator;

@RestController
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/users")
    public Flux<User> list(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "3") Long count
    ) {

        return Flux
                .from(userService.list())
                .sort(Comparator.comparing(User::getId) );
    }

    @PostMapping
    public Mono<User> add(@RequestBody User user) {
        return userService.addOne(user);
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
