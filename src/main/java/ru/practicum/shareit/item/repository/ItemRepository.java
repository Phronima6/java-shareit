package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import java.util.Collection;

public interface ItemRepository {

    // Добавление вещи
    Item createItem(final Item item);

    // Получение всех вещей
    Collection<Item> getAllItems();

    // Получение всех вещей пользователя
    Collection<Item> getAllItemsFromUser(final Integer userId);

    // Получение вещи
    Item getItem(final Integer itemId);

    // Обновление вещи
    Item updateItem(final Integer itemId, final Item item);

    // Поиск вещи по тексту
    Collection<Item> searchItem(final String text);

}