package flux.admintools.service.repo;

import flux.admintools.domen.activity.Activity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ActivityRepo extends ReactiveCrudRepository<Activity, Long> {
}
