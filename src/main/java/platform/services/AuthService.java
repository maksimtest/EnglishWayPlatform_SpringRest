package platform.services;


import platform.dtos.JwtRequest;
import platform.dtos.JwtResponse;
import platform.dtos.RegistrationUserDto;
import platform.dtos.RememberPasswordDto;
import platform.entities.User;
import platform.exceptions.ApiExceptionFactory;
import platform.utils.CodeUtil;
import platform.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import platform.utils.TimeUtils;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final TimeUtils timeUtils;
    private final EmailService emailService;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ApiExceptionFactory.get(ApiExceptionFactory.INVALID_CREDENTIALS);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            System.out.println("AuthService, username is present");
            return ApiExceptionFactory.get(ApiExceptionFactory.USERNAME_ALREADY_EXISTS);
        }
        if (userService.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            System.out.println("AuthService, email is present");
            return ApiExceptionFactory.get(ApiExceptionFactory.EMAIL_ALREADY_EXISTS);
        }
        String code = CodeUtil.getCode(true);
        try {
            User user = userService.createNewUser(registrationUserDto, code);
            emailService.sendCodeAfterRegistration(user, code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiExceptionFactory.get(ApiExceptionFactory.UNKNOWN_PROBLEM_WITH_REGISTRATION);
    }
    public ResponseEntity<?> simpleCreateNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            System.out.println("AuthService, username is present");
            return ApiExceptionFactory.get(ApiExceptionFactory.USERNAME_ALREADY_EXISTS);
        }
        if (userService.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            System.out.println("AuthService, email is present");
            return ApiExceptionFactory.get(ApiExceptionFactory.EMAIL_ALREADY_EXISTS);
        }
        String code = CodeUtil.getCode(true);
        try {
            User user = userService.createNewUser(registrationUserDto, code);
            user.setActive(true);
            userService.setActivateCode(user, null);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiExceptionFactory.get(ApiExceptionFactory.UNKNOWN_PROBLEM_WITH_REGISTRATION);
    }
    public ResponseEntity<?> changePassword(RememberPasswordDto rememberPasswordDto) {
        String code = rememberPasswordDto.getCode();
        User user = userService.findByCode(code).orElse(new User());
        if (!code.equalsIgnoreCase(user.getActivationCode())) {
            return ApiExceptionFactory.get(ApiExceptionFactory.UNSUCCESSFUL_ATTEMPT_TO_CHANGE_PASSWORD);
        }
        userService.changePasswordWithoutSaving(user, rememberPasswordDto.getPassword());
        userService.setActivateCode(user, null);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> rememberPassword(String email) {
        User user = userService.findByEmail(email).orElse(new User());
        if (!email.equalsIgnoreCase(user.getEmail())) {
            return ApiExceptionFactory.get(ApiExceptionFactory.REMEMBER_EMAIL_IS_INVALID);
        }
        String code = CodeUtil.getCode(false);
        try {
            userService.setActivateCode(user, code);
            emailService.sendCodeAfterRememberPassword(user, code);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiExceptionFactory.get(ApiExceptionFactory.UNKNOWN_PROBLEM_WITH_REMEMBER_PASSWORD);
    }


    public ResponseEntity<?> activateCode(String activateCode) {
        User user = userService.findByCode(activateCode).orElse(new User());
        if (!activateCode.equalsIgnoreCase(user.getActivationCode())) {
            System.out.println("AuthService, ACTIVATE_CODE_IS_INVALID: activateCode="+activateCode+", user.getActivationCode()="+user.getActivationCode());
            return ApiExceptionFactory.get(ApiExceptionFactory.ACTIVATE_CODE_IS_INVALID);
        }
        if (timeUtils.expiration(user.getCodeExpiration())) {
            userService.setActivateCode(user, null);
            return ApiExceptionFactory.get(ApiExceptionFactory.ACTIVATE_CODE_IS_EXPIRED);
        }
        if (CodeUtil.isRegistrationCode(activateCode)) {
            user.setActive(true);
            userService.setActivateCode(user, null);
        }
        return ResponseEntity.ok().build();
    }
}