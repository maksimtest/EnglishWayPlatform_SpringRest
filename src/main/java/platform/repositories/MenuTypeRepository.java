package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.MenuType;
import platform.entities.Role;

import java.util.Optional;

@Repository
public interface MenuTypeRepository extends CrudRepository<MenuType, Long> {
    MenuType findByName(String name);

    default MenuType getMainMenuType(){
        return findByName("main");
    }

    default MenuType getLessonMenuType(){
        return findByName("lesson");
    }
}
