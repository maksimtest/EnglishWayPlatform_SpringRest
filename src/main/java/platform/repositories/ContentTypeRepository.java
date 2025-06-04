package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Content;
import platform.entities.ContentType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentTypeRepository extends CrudRepository<ContentType, Long> {
    Optional<ContentType> findByName(String name);
}
