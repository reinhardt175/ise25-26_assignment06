package de.seuhd.campuscoffee.api.mapper;

/*
public interface UserDtoMapper {
TODO: Implement user DTO mapper
*/

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.domain.model.User;
import org.springframework.stereotype.Component;


/**
 * Mapper for converting between domain model objects and DTOs.
 * This mapper handles the translation between the {@link User} domain model and the
 * {@link UserDto}.
 * This is part of the API layer adapter in the hexagonal architecture, enabling the
 * domain layer to remain independent of API concerns.
 */

@Component
public class UserDtoMapper {
    /**
     * Converts User domain object to UserDto
     */
    public UserDto toUserDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
            .id(user.id())
            .createdAt(user.createdAt())
            .updatedAt(user.updatedAt())
            .loginName(user.loginName())
            .emailAddress(user.emailAddress())
            .firstName(user.firstName())
            .lastName(user.lastName())
            .build();
    }

    /**
     * Converts UserDto to User domain object
     */

    public User toUser(UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
            .id(userDto.id())
            .createdAt(userDto.createdAt())
            .updatedAt(userDto.updatedAt())
            .loginName(userDto.loginName())
            .emailAddress(userDto.emailAddress())
            .firstName(userDto.firstName())
            .lastName(userDto.lastName())
            .build();


    }
}