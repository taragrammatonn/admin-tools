package flux.admintools.controller;

import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
