package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import platform.entities.LevelTestResult;

import java.util.List;

@Repository
public interface LevelTestResultRepository extends CrudRepository<LevelTestResult, Integer> {
    <S extends LevelTestResult> S save(S entity);

    @Query("SELECT r FROM LevelTestResult r WHERE r.user.id = :userId ORDER BY r.time DESC")
    List<LevelTestResult> findByUserId(@Param("userId") Long userId);
    /*
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(String code);

    @Override
    <S extends User> S save(S entity);

    @Query("SELECT r FROM TestResult r WHERE r.user.id = :userId")
    List<TestResult> findByUserId(@Param("userId") Long userId);

* */
}
