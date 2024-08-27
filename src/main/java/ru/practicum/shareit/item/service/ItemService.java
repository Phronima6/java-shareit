package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import java.util.Collection;

public interface ItemService {

    // Добавление вещи
    ItemDto createItem(final Integer userId, final ItemDto itemDto);

    // Получение всех вещей пользователя
    Collection<ItemDto> getAllItemsFromUser(final Integer userId);

    // Получение вещи
    ItemDto getItem(final Integer itemId);

    // Обновление вещи
    ItemDto updateItem(final Integer userId, final Integer itemId, final ItemDto itemDto);

    // Поиск вещи по тексту
    Collection<ItemDto> searchItem(final String text);

}