package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Unit;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {
}
