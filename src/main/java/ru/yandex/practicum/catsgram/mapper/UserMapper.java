package ru.yandex.practicum.catsgram.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.catsgram.dto.user.NewUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UpdateUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UserDto;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }

    public static User toUser(NewUserRequest user) {
        User dto = new User();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRegistrationDate(Instant.now());
        return dto;
    }

    public static User toUser(User user, UpdateUserRequest request) {
        if (request.hasEmail()) {
            user.setEmail(request.getEmail());
        }
        if (request.hasPassword()) {
            user.setPassword(request.getPassword());
        }
        if (request.hasUsername()) {
            user.setUsername(request.getUsername());
        }
        return user;
    }

}
