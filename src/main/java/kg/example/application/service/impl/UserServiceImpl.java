package kg.example.application.service.impl;

import kg.example.application.dto.UserDto;
import kg.example.application.dto.UserUpdateDto;
import kg.example.application.entity.User;
import kg.example.application.mapper.UserMapper;
import kg.example.application.repository.UserRepository;
import kg.example.application.service.UserService;
import kg.example.application.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUser() {
        return userMapper.toUserDto(UserUtils.getCurrentUser());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.toUserDto(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserDto);
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        User user = UserUtils.getCurrentUser();
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(userUpdateDto.getProfileImageUrl());
        }
        user = userRepository.save(user);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto selfDelete() {
        User user = UserUtils.getCurrentUser();
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }
}
