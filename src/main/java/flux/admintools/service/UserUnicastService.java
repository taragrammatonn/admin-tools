package flux.admintools.service;

import flux.admintools.domen.users.User;
import reactor.core.publisher.Flux;

public interface UserUnicastService {

    void onNext(User next);

    Flux<User> getMessages();
}
