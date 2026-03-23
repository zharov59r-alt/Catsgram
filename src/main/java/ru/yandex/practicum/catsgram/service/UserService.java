package ru.yandex.practicum.catsgram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.user.NewUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UpdateUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UserDto;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.UserMapper;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(long userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + userId));
    }

    public UserDto save(NewUserRequest request) {

        // проверяем выполнение необходимых условий
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (userRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        User user = UserMapper.toUser(request);

        user.setRegistrationDate(Instant.now());

        user = userRepository.save(user);

        return UserMapper.toUserDto(user);
    }

    public UserDto update(long userId, UpdateUserRequest request) {

        User user = userRepository.findById(userId)
                .map(u -> UserMapper.toUser(u, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (userRepository.findAllByEmail(user.getEmail()).size() > 1) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        user = userRepository.update(user);
        return UserMapper.toUserDto(user);

    }

}
