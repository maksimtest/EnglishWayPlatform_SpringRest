package platform.controllers;

import org.springframework.web.bind.annotation.*;
import platform.dtos.JwtRequest;
import platform.dtos.RegistrationUserDto;
import platform.dtos.RememberPasswordDto;
import platform.dtos.UserDto;
import platform.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import platform.utils.CodeUtil;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        System.out.println("AuthController.auth");
        return authService.createAuthToken(authRequest);
    }
    @PostMapping("/reg")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        System.out.println("AuthController.reg");
        return authService.createNewUser(registrationUserDto);
    }
    @GetMapping("/activate")
    public ResponseEntity<?> activateCode(@RequestParam("code") String activateCode) {
        System.out.println("AuthController.activeCode, activateCode="+activateCode);
        ResponseEntity<?> resp = authService.activateCode(activateCode);
        System.out.println("AuthController.activeCode, resp: " + resp);
        return resp;
    }
    @PostMapping("/remember")
    public ResponseEntity<?> remember(@RequestBody UserDto userDto) {
        String email = userDto.getEmail();
        System.out.println("AuthController.remember, email="+email);
        return authService.rememberPassword(email);
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody RememberPasswordDto rememberPasswordDto) {
        System.out.println("AuthController.changePassword, password="+rememberPasswordDto);
        return authService.changePassword(rememberPasswordDto);
    }
    @PostMapping("/simple-reg")
    public ResponseEntity<?> simpleCreateNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        System.out.println("AuthController.simpleCreateNewUser");
        return authService.simpleCreateNewUser(registrationUserDto);
    }
}