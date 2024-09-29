package ru.practicum.shareit.user.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    UserService userService;
    static final String PATH_USER_ID = "user-id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody final UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{" + PATH_USER_ID + "}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(PATH_USER_ID) final Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{" + PATH_USER_ID + "}")
    public UserDto getUser(@PathVariable(PATH_USER_ID) final Long userId) {
        return userService.getUser(userId);
    }


    @PatchMapping("/{" + PATH_USER_ID + "}")
    public UserDto updateUser(@PathVariable(PATH_USER_ID) final Long userId,
                              @RequestBody final UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

}