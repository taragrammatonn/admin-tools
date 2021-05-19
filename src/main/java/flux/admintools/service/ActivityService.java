package flux.admintools.service;

import flux.admintools.domen.activity.Activity;
import flux.admintools.domen.history.UserAction;
import flux.admintools.domen.users.User;
import flux.admintools.service.repo.ActivityRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ActivityService {

    private final ActivityRepo activityRepo;

    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Mono<Void> loginUser(User user) {
        var entity = new Activity(
                UserAction.LOGIN,
                user.getId(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
        return activityRepo.save(entity).then();
    }

    public Mono<Void> logoutUser(User user) {
        return activityRepo.save(new Activity(
                UserAction.LOGOUT,
                user.getId(),
                LocalDateTime.now(),
                null,
                LocalDateTime.now()
        )).then();
    }
}
