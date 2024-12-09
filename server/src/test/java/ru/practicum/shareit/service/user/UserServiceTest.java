package ru.practicum.shareit.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImplements;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImplements userService;

    @Test
    void createUserSuccess() {
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto result = userService.createUser(userDto);
        assertNotNull(result);
    }

    @Test
    void deleteUserSuccess() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUserSuccess() {
        Long userId = 1L;
        User user = new User();
        UserDto userDto = new UserDto();
        when(userRepository.getById(userId)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto result = userService.getUser(userId);
        assertNotNull(result);
    }

    @Test
    void updateUserSuccess() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User user = new User();
        User userOld = new User();
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.getById(userId)).thenReturn(userOld);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto result = userService.updateUser(userId, userDto);
        assertNotNull(result);
    }

    @Test
    void updateUserEmailIsEmpty() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User user = new User();
        User userOld = new User();
        userOld.setEmail("old@example.com");
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.getById(userId)).thenReturn(userOld);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto result = userService.updateUser(userId, userDto);
        assertNotNull(result);
        assertEquals("old@example.com", user.getEmail());
    }

    @Test
    void updateUserNameIsEmpty() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User user = new User();
        User userOld = new User();
        userOld.setName("Old Name");
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.getById(userId)).thenReturn(userOld);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);
        UserDto result = userService.updateUser(userId, userDto);
        assertNotNull(result);
        assertEquals("Old Name", user.getName());
    }

}