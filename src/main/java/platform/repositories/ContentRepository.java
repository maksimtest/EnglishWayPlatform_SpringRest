package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Content;

import java.util.List;

@Repository
public interface ContentRepository extends CrudRepository<Content, Long> {

    //@Query("SELECT r FROM LevelTestResult r WHERE r.user.id = :userId ORDER BY r.time DESC")
    @Query("SELECT c FROM Content c WHERE c.menuItem.id = :menuItemId ORDER BY c.order ASC")
    List<Content> findAllContentsByMenuAndOrdered(Long menuItemId);
}
