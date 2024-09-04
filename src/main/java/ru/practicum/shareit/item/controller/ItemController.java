package ru.practicum.shareit.item.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") @Positive final Integer userId,
                              @RequestBody @Validated(CreateGroup.class) final ItemDto itemDto) {
        return itemService.createItem(userId, itemDto);
    }

    @GetMapping
    public Collection<ItemDto> getAllItemsFromUser(@RequestHeader("X-Sharer-User-Id") @Positive final Integer userId) {
        return itemService.getAllItemsFromUser(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable @Positive final Integer itemId) {
        return itemService.getItem(itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") @Positive final Integer userId,
                              @PathVariable @Positive final Integer itemId,
                              @RequestBody final ItemDto itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam final String text) {
        return itemService.searchItem(text);
    }

}