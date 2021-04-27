package flux.admintools.service;

import flux.admintools.domen.users.User;
import flux.admintools.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService, UserUnicastService {

    private EmitterProcessor<User> processor = EmitterProcessor.create();

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Flux<User> list() {
        return userRepo.findAll();
    }

    public Mono<User> addOne(User user) {
        return userRepo.save(user);
    }

    public Mono<User> getOne(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepo.findByUsername(username)
                .cast(UserDetails.class);
    }

    @Override
    public void onNext(User next) {
        processor.onNext(next);
    }

    @Override
    public Flux<User> getMessages() {
        return processor.publish().autoConnect();
    }
}
