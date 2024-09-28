package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import java.util.Collection;

public interface ItemRequestService {

    // Добавление запроса
    ItemRequestDto createItemRequest(final Long userId, final ItemRequestDto itemRequestDto);

    // Получение запросов пользователя
    Collection<ItemRequestDto> getAllFromRequestor(final Long userId);

    // Получение запроса
    ItemRequestDto getItemRequest(final Long userId, final Long requestId);

    // Получение всех запросов других пользователей
    Collection<ItemRequestDto> getOtherItemRequests(final Long userId);

}