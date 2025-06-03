package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Settings;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingsRepository extends CrudRepository<Settings, Long> {
    Optional<Settings> findByCode(String code);
    List<Settings> findAllByOrderByIdAsc();
}
