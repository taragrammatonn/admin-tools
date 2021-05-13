package flux.admintools.service.repo;

import com.fasterxml.jackson.annotation.JsonView;
import flux.admintools.domen.Views;
import flux.admintools.domen.users.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveSortingRepository<User, Long> {

    @JsonView(Views.GeneralUserInfo.class)
    Mono<User> findByUsername(@Param("userName") String userName);
}