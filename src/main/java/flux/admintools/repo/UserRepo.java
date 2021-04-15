package flux.admintools.repo;

import flux.admintools.domen.users.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveSortingRepository<User, Long> {

    Mono<User> findByUsername(@Param("userName") String userName);
}