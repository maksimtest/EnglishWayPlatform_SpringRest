package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Course;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAllByOrderByLevelIdAsc();

    //void deleteById(Long id);

    //Optional<Course> save(Course course);
    /*
*     List<LevelTest> findAllByOrderByNumAsc();
    Optional<LevelTest> findById(Long id);
    Optional<LevelTest> findFirstByNum(int num);

    @Override
    void deleteById(Long id);

    LevelTest save(LevelTest test);

    default void save(LevelTest test1, LevelTest test2){
        save(test1);
        save(test2);
    }

* */
}
