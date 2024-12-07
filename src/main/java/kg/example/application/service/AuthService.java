package kg.example.application.service;

import kg.example.application.dto.UserLoginDto;
import kg.example.application.dto.UserLoginResponseDto;
import kg.example.application.dto.UserRegisterDto;


public interface AuthService {
    String register(UserRegisterDto userRegisterDto);
    UserLoginResponseDto login(UserLoginDto userLoginDto);
    String restorePassword(String emal);
    void resetPassword(String token, String newPassword);
}
