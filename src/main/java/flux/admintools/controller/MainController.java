package flux.admintools.controller;

import flux.admintools.domen.users.User;
import flux.admintools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<User> list(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "3") Long count
    ) {
        return userService.list();
    }

    @PostMapping
    public Mono<User> add(@RequestBody User user) {
        return userService.addOne(user);
    }
}
