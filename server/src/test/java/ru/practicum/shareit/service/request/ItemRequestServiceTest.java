package ru.practicum.shareit.service.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestServiceImplements;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {

    @Mock
    private ItemMapper itemMapper;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemRequestMapper itemRequestMapper;
    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ItemRequestServiceImplements itemRequestService;

    @Test
    void createItemRequestUserNotFound() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> itemRequestService.createItemRequest(userId, itemRequestDto));
        assertEquals("Ошибка при создании запроса. Пользователя с id = " + userId + " нет.",
                exception.getMessage());
    }

    @Test
    void createItemRequestSuccess() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        User user = new User();
        ItemRequest itemRequest = new ItemRequest();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestMapper.toItemRequest(itemRequestDto)).thenReturn(itemRequest);
        when(itemRequestRepository.save(itemRequest)).thenReturn(itemRequest);
        when(itemRequestMapper.toItemRequestDto(itemRequest)).thenReturn(itemRequestDto);
        when(userMapper.toUserDto(user)).thenReturn(new UserDto());
        ItemRequestDto result = itemRequestService.createItemRequest(userId, itemRequestDto);
        assertNotNull(result);
    }

    @Test
    void getItemRequestUserNotFound() {
        Long userId = 1L;
        Long requestId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> itemRequestService.getItemRequest(userId, requestId));
        assertEquals("Ошибка при получении запроса. Пользователя с id = " + userId + " нет.",
                exception.getMessage());
    }

    @Test
    void getItemRequestRequestNotFound() {
        Long userId = 1L;
        Long requestId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(requestId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> itemRequestService.getItemRequest(userId, requestId));
        assertEquals("Ошибка при получении запроса. Запроса с id = " + requestId + "нет.",
                exception.getMessage());
    }

}