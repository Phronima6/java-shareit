package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import java.util.Collection;

public interface ItemService {

    // Добавление отзыва
    CommentDto createComment(final Long userId, final Long itemId, final CommentDto commentDto);

    // Добавление вещи
    ItemDto createItem(final Long userId, final ItemDto itemDto);

    // Получение всех вещей пользователя
    Collection<ItemDto> getAllItemsFromUser(final Long userId);

    // Получение вещи
    ItemDto getItem(final Long userId, final Long itemId);

    // Обновление вещи
    ItemDto updateItem(final Long userId, final Long itemId, final ItemDto itemDto);

    // Поиск вещи по тексту
    Collection<ItemDto> searchItem(final String text);

}