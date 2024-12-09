package ru.practicum.shareit.request.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ItemRequestServiceImplements implements ItemRequestService {

    ItemMapper itemMapper;
    ItemRepository itemRepository;
    ItemRequestMapper itemRequestMapper;
    ItemRequestRepository itemRequestRepository;
    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    @Transactional
    public ItemRequestDto createItemRequest(final Long userId, final ItemRequestDto itemRequestDto) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Ошибка при создании запроса."
                        + " Пользователя с id = " + userId + " нет."));
        final ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setRequestor(user);
        itemRequestRepository.save(itemRequest);
        final ItemRequestDto request = itemRequestMapper.toItemRequestDto(itemRequest);
        request.setRequestor(userMapper.toUserDto(user));
        return request;
    }

    @Override
    public Collection<ItemRequestDto> getAllFromRequestor(final Long userId) {
        return itemRequestRepository.getAllByRequestor(userId).stream()
                .map(itemRequest -> {
                    ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);
                    List<ItemDto> items = itemRepository.findAllByRequestId(itemRequest.getId()).stream()
                            .map(itemMapper::toItemDto)
                            .toList();
                    itemRequestDto.setItems(items);
                    itemRequestDto.setRequestor(userMapper.toUserDto(itemRequest.getRequestor()));
                    return itemRequestDto;
                })
                .toList();
    }

    @Override
    public ItemRequestDto getItemRequest(final Long userId, final Long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Ошибка при получении запроса."
                + " Пользователя с id = " + userId + " нет."));
        final ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Ошибка при получении запроса."
                        + " Запроса с id = " + requestId + "нет."));
        final ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);
        final List<ItemDto> items = itemRepository.findAllByRequestId(itemRequest.getId()).stream()
                .map(itemMapper::toItemDto)
                .toList();
        itemRequestDto.setItems(items);
        itemRequestDto.setRequestor(userMapper.toUserDto(itemRequest.getRequestor()));
        return itemRequestDto;
    }

    @Override
    public Collection<ItemRequestDto> getOtherItemRequests(final Long userId) {
        return itemRequestRepository.findAllExcludingUserId(userId).stream()
                .map(itemRequest -> {
                    ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);
                    List<ItemDto> items = itemRepository.findAllByRequestId(itemRequest.getId()).stream()
                            .map(itemMapper::toItemDto)
                            .toList();
                    itemRequestDto.setItems(items);
                    itemRequestDto.setRequestor(userMapper.toUserDto(itemRequest.getRequestor()));
                    return itemRequestDto;
                })
                .toList();
    }

}