package kg.example.application.controller;

import kg.example.application.dto.UserLoginDto;
import kg.example.application.dto.UserLoginResponseDto;
import kg.example.application.dto.UserRegisterDto;
import kg.example.application.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        return new ResponseEntity<>(authService.register(userRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authService.login(userLoginDto));
    }

    @PostMapping("/restore-password")
    public ResponseEntity<String> restorePassword(@RequestParam String email) {
        return ResponseEntity.ok(authService.restorePassword(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.noContent().build();
    }

}

