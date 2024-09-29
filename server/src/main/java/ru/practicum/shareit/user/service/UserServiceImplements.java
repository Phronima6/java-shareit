package ru.practicum.shareit.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImplements implements UserService {

    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    public UserDto createUser(final UserDto userDto) {
        final User user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(final Long userId) {
        return userMapper.toUserDto(userRepository.getById(userId));
    }

    @Override
    public UserDto updateUser(final Long userId, final UserDto userDto) {
        final User user = userMapper.toUser(userDto);
        final User userOld = userRepository.getById(userId);
        if (user.getEmail() == null) {
            log.info("UserServiceImplements, updateUser, emailIsEmpty.");
            user.setEmail(userOld.getEmail());
        }
        user.setId(userId);
        if (user.getName() == null) {
            log.info("UserServiceImplements, updateUser, nameIsEmpty.");
            user.setName(userOld.getName());
        }
        return userMapper.toUserDto(userRepository.save(user));
    }

}