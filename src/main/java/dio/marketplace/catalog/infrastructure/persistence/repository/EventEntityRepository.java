package dio.marketplace.catalog.infrastructure.persistence.repository;

import dio.marketplace.catalog.infrastructure.persistence.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventEntityRepository extends CrudRepository<Event, UUID> {

}
