package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class ItemServiceImplements implements ItemService {

    ItemMapper itemMapper;
    ItemRepository itemRepository;
    UserRepository userRepository;

    // Проверка наличия вещи в базе данных
    public void checkItemId(final Integer itemId, String massageLog, String massageException) {
        if (itemRepository.getAllItems().stream()
                .map(Item::getId)
                .noneMatch(id -> id == itemId)) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + itemId +  "нет.");
        }
    }

    // Проверка наличия пользователя в базе данных
    public void checkUserId(final Integer userId, String massageLog, String massageException) {
        if (userRepository.getAllUsers().stream()
                .map(User::getId)
                .noneMatch(id -> id == userId)) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + userId +  "нет.");
        }
    }

    @Override
    public ItemDto createItem(final Integer userId, final ItemDto itemDto) {
        checkUserId(userId, "ItemServiceImplements, createItem.",
                "Ошибка при попытке добавления вещи. Пользователя с таким id = ");
        final Item item = itemMapper.toItem(itemDto);
        item.setOwner(userRepository.getUser(userId));
        return itemMapper.toItemDto(itemRepository.createItem(item));
    }

    @Override
    public Collection<ItemDto> getAllItemsFromUser(final Integer userId) {
        checkUserId(userId, "ItemServiceImplements, findAllItemsFromUser.",
                "Ошибка при попытке получения всех вещей пользователя. Пользователя с таким id = ");
        return itemRepository.getAllItemsFromUser(userId).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto getItem(final Integer itemId) {
        checkItemId(itemId, "ItemServiceImplements, findItem.",
                "Ошибка при попытке получить вещь. Вещи с таким id = ");
        return itemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    @Override
    public ItemDto updateItem(final Integer userId,  final Integer itemId, final ItemDto itemDto) {
        checkItemId(itemId, "ItemServiceImplements, updateItem.",
                "Ошибка при попытке обновить вещь. Вещи с таким id = ");
        checkUserId(userId, "ItemServiceImplements, updateItem.",
                "Ошибка при попытке обновить вещь. Пользователя с таким id = ");
        final Item itemOld = itemRepository.getItem(itemId);
        if (itemOld.getOwner().getId() != userId) {
            log.warn("ItemServiceImplements, updateItem.");
            throw new NotFoundException("Ошибка при попытке обновить вещь. Эта вещь не принадлежит пользователю"
                    + " с таким id = " + userId + ".");
        }
        final Item item = itemMapper.toItem(itemDto);
        if (item.getAvailable() == null) {
            log.info("ItemServiceImplements, updateItem, availableIsEmpty.");
            item.setAvailable(itemOld.getAvailable());
        }
        if (item.getDescription() == null) {
            log.info("ItemServiceImplements, updateItem, descriptionIsEmpty.");
            item.setDescription(itemOld.getDescription());
        }
        if (item.getName() == null) {
            log.info("ItemServiceImplements, updateItem, nameIsEmpty.");
            item.setName(itemOld.getName());
        }
        item.setOwner(userRepository.getUser(userId));
        return itemMapper.toItemDto(itemRepository.updateItem(itemId, item));
    }

    @Override
    public Collection<ItemDto> searchItem(final String text) {
        if (text.isEmpty()) {
            log.info("ItemServiceImplements, searchItem, textIsEmpty.");
            return new ArrayList<>();
        }
        return itemRepository.searchItem(text.toLowerCase().trim()).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

}