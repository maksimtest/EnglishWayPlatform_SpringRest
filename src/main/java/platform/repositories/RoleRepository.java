package platform.repositories;

import platform.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    default Role getStudentRole(){
        return findByName("ROLE_ADMIN").orElseThrow();
    }
}
