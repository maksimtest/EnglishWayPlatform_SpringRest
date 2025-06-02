package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Level;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {
    List<Level> findAllByOrderByCodeAsc();
    default Level getFirst(){
        return findById(1L).orElse(new Level());
    }

    @Override
    Optional<Level> findById(Long aLong);
}
