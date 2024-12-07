package kg.example.application.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String extractUsername(String token);

    Date extractExpiration(String token);

    String generateJwtToken(UserDetails userDetails);

    Boolean checkJwtToken(String token, UserDetails userDetails);
}
