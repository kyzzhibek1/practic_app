package kg.example.application.service.impl;

import kg.example.application.dto.UserLoginDto;
import kg.example.application.dto.UserLoginResponseDto;
import kg.example.application.dto.UserRegisterDto;
import kg.example.application.entity.User;
import kg.example.application.repository.UserRepository;
import kg.example.application.service.AuthService;
import kg.example.application.service.EmailService;
import kg.example.application.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final EmailService emailService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository userRepository, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        validateRegister(userRegisterDto);

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        user = userRepository.save(user);

        return String.format("User with username %s created", user.getUsername());
    }

    private void validateRegister(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        User existingUser = userRepository.findByEmail(userRegisterDto.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already in use");
        }
        existingUser = userRepository.findByUsername(userRegisterDto.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already in use");
        }
        if (userRegisterDto.getPassword() == null || userRegisterDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (userRegisterDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }

    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                userLoginDto.getPassword()));
        String jwtToken = jwtService.generateJwtToken(user);
        return new UserLoginResponseDto(jwtToken);
    }

    @Override
    public String restorePassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User with the given email does not exist");
        }

        String token = jwtService.generateJwtToken(user);

        String subject = "Password Reset Request";
        String body = String.format(
                "Hello %s, your token for password reset: %s", user.getUsername(), token);

        emailService.sendEmail(user.getEmail(), subject, body);

        return "Password reset token sent to your email.";
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
