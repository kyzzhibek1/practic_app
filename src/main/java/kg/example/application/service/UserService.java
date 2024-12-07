package kg.example.application.service;

import kg.example.application.dto.UserDto;
import kg.example.application.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getUser();
    UserDto getUserById(Long id);
    Page<UserDto> getUsers(Pageable pageable);
    UserDto updateUser(UserUpdateDto userUpdateDto);
    UserDto selfDelete();
}
