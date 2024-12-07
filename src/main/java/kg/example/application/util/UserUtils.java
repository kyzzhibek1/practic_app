package kg.example.application.util;

import kg.example.application.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtils {

    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new IllegalArgumentException("Current user not found");
        }
        return user;
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}
