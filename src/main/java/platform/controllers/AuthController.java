package platform.controllers;

import org.springframework.web.bind.annotation.*;
import platform.dtos.JwtRequest;
import platform.dtos.RegistrationUserDto;
import platform.dtos.RememberPasswordDto;
import platform.dtos.UserDto;
import platform.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        System.out.println("AuthController.auth, authRequest="+authRequest);
        ResponseEntity<?> result = authService.createAuthToken(authRequest);
        System.out.println("AuthController.auth, result="+result);
        return result;
    }
    @PostMapping("/auth/reg")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        System.out.println("AuthController.reg");
        ResponseEntity<?> result = authService.createNewUser(registrationUserDto);
        System.out.println("AuthController.reg, result="+result);
        return result;
    }
    @GetMapping("/auth/activate")
    public ResponseEntity<?> activateCode(@RequestParam("code") String activateCode) {
        System.out.println("AuthController.activeCode, activateCode="+activateCode);
        ResponseEntity<?> resp = authService.activateCode(activateCode);
        System.out.println("AuthController.activeCode, resp: " + resp);
        return resp;
    }
    @PostMapping("/auth/remember")
    public ResponseEntity<?> remember(@RequestBody UserDto userDto) {
        String email = userDto.getEmail();
        System.out.println("AuthController.remember, email="+email);
        return authService.rememberPassword(email);
    }
    @PostMapping("/auth/change-password")
    public ResponseEntity<?> changePassword(@RequestBody RememberPasswordDto rememberPasswordDto) {
        System.out.println("AuthController.changePassword, password="+rememberPasswordDto);
        return authService.changePassword(rememberPasswordDto);
    }
    @PostMapping("/auth/quick-reg")
    public ResponseEntity<?> quickCreateNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        System.out.println("AuthController.quickCreateNewUser, answers="+registrationUserDto.getCode());
        return authService.quickCreateUserAfterLevelTest(registrationUserDto);
    }
}