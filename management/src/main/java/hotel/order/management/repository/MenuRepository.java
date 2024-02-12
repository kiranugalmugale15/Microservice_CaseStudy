package hotel.order.management.repository;

import hotel.order.management.model.Menu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MenuRepository extends ReactiveCrudRepository<Menu, Long> {
}
