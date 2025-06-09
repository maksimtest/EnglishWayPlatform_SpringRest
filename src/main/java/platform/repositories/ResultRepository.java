package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Result;
import platform.entities.Unit;
import platform.entities.User;

import java.util.List;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.student.id = :userId AND r.content.id = :contentId AND r.last=true")
    List<Result> findLastByUserAndContentId(Long userId, Long contentId);

    List<Result> findAllByStudentAndUnit(User student, Unit unit);

    default Result getLastResult(Long userId, Long contentId){
        List<Result> results = findLastByUserAndContentId(userId, contentId);
        if(results.isEmpty()){
            return null;
        }
        return results.get(0);
    }
}
