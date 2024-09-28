package ru.practicum.shareit.user.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.user.dto.UserDto;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    UserClient userClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody @Validated(CreateGroup.class) final UserDto userDto) {
        return userClient.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive final Long userId) {
        userClient.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable @Positive final Long userId) {
        return userClient.getUser(userId);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable @Positive final Long userId,
                                             @RequestBody final UserDto userDto) {
        return userClient.updateUser(userId, userDto);
    }

}