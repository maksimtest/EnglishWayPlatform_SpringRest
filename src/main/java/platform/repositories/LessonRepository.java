package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Lesson;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
