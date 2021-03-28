package flux.admintools.repo;

import flux.admintools.domen.users.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT * FROM users.\"user\" WHERE username = :userName")
    Mono<User> findByUserName(@Param("userName") String userName);

    @Query("SELECT * FROM users.\"user\"")
    Flux<User> getAllUsers();
}
