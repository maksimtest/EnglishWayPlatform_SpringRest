package platform.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.MenuItem;
import platform.entities.MenuType;
import platform.entities.Role;

import java.util.List;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
    //@Query("SELECT item FROM MenuItem item WHERE item.lesson.id = :LessonId ORDER BY item.order ASC")
    List<MenuItem> findByType(MenuType type);

    List<MenuItem> findByTypeAndRole(MenuType type, Role role);

    @Query("SELECT item FROM MenuItem item WHERE item.lesson.id = :LessonId ORDER BY item.order ASC")
    List<MenuItem> findByLessonId(Long LessonId);
}
