package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.LevelTest;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelTestRepository extends CrudRepository<LevelTest, Long> {
    List<LevelTest> findAllByOrderByLevelIdAscNumAsc();
    Optional<LevelTest> findById(Long id);
    Optional<LevelTest> findFirstByNum(int num);

//    @Override
//    void deleteById(Long id);

   // LevelTest save(LevelTest test);

    default void save(LevelTest test1, LevelTest test2){
        save(test1);
        save(test2);
    }
}
