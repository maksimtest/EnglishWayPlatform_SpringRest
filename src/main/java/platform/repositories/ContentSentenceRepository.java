package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.ContentSentence;


@Repository
public interface ContentSentenceRepository extends CrudRepository<ContentSentence, Long> {
}
