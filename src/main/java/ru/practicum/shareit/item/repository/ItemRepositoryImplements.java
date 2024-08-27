package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ItemRepositoryImplements implements ItemRepository {

    Map<Integer, Item> items = new HashMap<>();

    @Override
    public Item createItem(final Item item) {
        item.setId(getNextId());
        log.info("ItemRepositoryImplements, createItem, itemId created = {}.", item.getId());
        items.put(item.getId(), item);
        log.info("ItemRepositoryImplements, createItem, Item created.");
        return getItem(item.getId());
    }

    @Override
    public Collection<Item> getAllItems() {
        return items.values();
    }

    @Override
    public Collection<Item> getAllItemsFromUser(final Integer userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .toList();
    }

    @Override
    public Item getItem(final Integer itemId) {
        return items.get(itemId);
    }

    @Override
    public Item updateItem(final Integer itemId, final Item item) {
        items.put(itemId, item);
        log.info("ItemRepositoryImplements, updateItem, Item updated.");
        return getItem(itemId);
    }

    @Override
    public Collection<Item> searchItem(final String text) {
        return items.values().stream()
                .filter(item -> item.getAvailable()
                        && (item.getName().toLowerCase().contains(text)
                        || item.getDescription().toLowerCase().contains(text)))
                .toList();
    }

    // Генератор целочисленного идентификатора пользователя
    public Integer getNextId() {
        int currentMaxId = items.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}