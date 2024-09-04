package ru.practicum.shareit.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImplements implements UserService {

    UserMapper userMapper;
    UserRepository userRepository;

    // Проверка email на повторяемость
    public void checkEmailUser(final User user, String massageLog, String massageException) {
        if (userRepository.getAllUsers().stream()
                .map(User::getEmail)
                .anyMatch(email -> email.equals(user.getEmail()))) {
            log.warn(massageLog);
            throw new DataException(massageException + user.getEmail() + " уже есть.");
        }
    }

    // Проверка наличия пользователя в базе данных
    public void checkUserId(final Integer userId, String massageLog, String massageException) {
        if (userRepository.getAllUsers().stream()
                .map(User::getId)
                .noneMatch(id -> id.equals(userId))) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + userId +  ".");
        }
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        final User user = userMapper.toUser(userDto);
        checkEmailUser(user, "UserServiceImplements, createUser.",
                "Ошибка при попытке добавления пользователя. Пользователь с таким email = ");
        return userMapper.toUserDto(userRepository.createUser(user));
    }

    @Override
    public void deleteUser(final Integer userId) {
        checkUserId(userId, "UserServiceImplements, deleteUser.",
                "Ошибка при попытке удаления пользователя. Не найден переданный id = ");
        userRepository.deleteUser(userId);
    }

    @Override
    public UserDto getUser(final Integer userId) {
        checkUserId(userId, "UserServiceImplements, findUser.",
                "Ошибка при попытке получения пользователя. Не найден переданный id = ");
        return userMapper.toUserDto(userRepository.getUser(userId));
    }

    @Override
    public UserDto updateUser(final Integer userId, final UserDto userDto) {
        checkUserId(userId, "UserServiceImplements, updateUser.",
                "Ошибка при попытке обновления пользователя. Не найден переданный id = ");
        final User user = userMapper.toUser(userDto);
        checkEmailUser(user, "UserServiceImplements, updateUser.",
                "Ошибка при попытке обновления пользователя. Пользователь с таким email = ");
        final User userOld = userRepository.getUser(userId);
        if (user.getEmail() == null) {
            log.info("UserServiceImplements, updateUser, emailIsEmpty.");
            user.setEmail(userOld.getEmail());
        }
        user.setId(userId);
        if (user.getName() == null) {
            log.info("UserServiceImplements, updateUser, nameIsEmpty.");
            user.setName(userOld.getName());
        }
        return userMapper.toUserDto(userRepository.updateUser(userId, user));
    }

}