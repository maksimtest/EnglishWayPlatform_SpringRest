package platform.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.entities.Menu;
import platform.entities.Role;

import java.util.Optional;

//@Repository
public interface MenuRepository /*extends CrudRepository<Menu, Long> */{
    Optional<Menu> findByRoleAndType(Role role, String type);
}
