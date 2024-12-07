package kg.example.application.mapper;

import kg.example.application.dto.UserDto;
import kg.example.application.dto.UserUpdateDto;
import kg.example.application.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toUserDto(User user);
    User toEntity(UserUpdateDto userUpdateDto);
    UserUpdateDto toUserUpdateDto(User user);
}