package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.dto.user.NewUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UpdateUserRequest;
import ru.yandex.practicum.catsgram.dto.user.UserDto;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable("userId") long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody NewUserRequest request) {
        return userService.save(request);
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable("userId") long userId, @RequestBody UpdateUserRequest request) {
        return userService.update(userId, request);
    }

}
