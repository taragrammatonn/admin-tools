package flux.admintools.service.repo;

import flux.admintools.domen.users.User;
import reactor.core.publisher.Flux;

public interface UserSinksService {

    void onNext(User next);

    Flux<User> getUsers();
}
