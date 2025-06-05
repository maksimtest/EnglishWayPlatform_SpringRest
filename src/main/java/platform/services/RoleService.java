package platform.services;

import platform.entities.Role;
import platform.entities.User;
import platform.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getStudentRole() {
        return roleRepository.findByName("ROLE_STUDENT").get();
    }
    public boolean isManagerRolePresent(User user){
        return user.getRoles()
                .stream().anyMatch(role -> role
                        .getName()
                        .equalsIgnoreCase("role_manager"));
    }
    public boolean isTeacherRolePresent(User user){
        return user.getRoles()
                .stream().anyMatch(role -> role
                        .getName()
                        .equalsIgnoreCase("role_teacher"));
    }
    public boolean isStudentRolePresent(User user){
        if(user.getRoles()==null || user.getRoles().isEmpty()){
            return true;
        }
        return user.getRoles()
                .stream().anyMatch(role -> role
                        .getName()
                        .equalsIgnoreCase("role_student"));
    }

}
