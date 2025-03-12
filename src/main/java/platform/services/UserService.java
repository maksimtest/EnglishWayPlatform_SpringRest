package platform.services;

import platform.dtos.RegistrationUserDto;
import platform.entities.User;
import platform.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.utils.TimeUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TimeUtils timesUtil;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByCode(String code) {
        return userRepository.findByActivationCode(code);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword()).roles();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto, String code) {
        User user = new User();
        user.setName(registrationUserDto.getName());
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setActive(false);
        user.setActivationCode(code);
        user.setCodeExpiration(timesUtil.getRegMinuteExpiration());
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }
    public void changePasswordWithoutSaving(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    public boolean setActivateCode(User user, String code) {
        user.setActivationCode(code);
        user.setCodeExpiration(code == null ? null : timesUtil.getRegMinuteExpiration());
        return userRepository.save(user) != null;
    }
}
