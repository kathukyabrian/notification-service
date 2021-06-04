package tech.kitucode.notification.repository;

import org.springframework.data.repository.CrudRepository;
import tech.kitucode.notification.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
}
