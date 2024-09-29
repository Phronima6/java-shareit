package ru.practicum.shareit.item.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    ItemService itemService;
    static final String PATH_COMMENT = "comment";
    static final String PATH_ITEM_ID = "item-id";
    static final String PATH_SEARCH = "/search";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PostMapping("/{" + PATH_ITEM_ID + "}/" + PATH_COMMENT)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestHeader(X_HEADER) final Long userId,
                                    @PathVariable(PATH_ITEM_ID) final Long itemId,
                                    @RequestBody final CommentDto commentDto) {
        return itemService.createComment(userId, itemId, commentDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader(X_HEADER) final Long userId,
                              @RequestBody final ItemDto itemDto) {
        return itemService.createItem(userId, itemDto);
    }

    @GetMapping
    public Collection<ItemDto> getAllItemsFromUser(@RequestHeader(X_HEADER) final Long userId) {
        return itemService.getAllItemsFromUser(userId);
    }

    @GetMapping("/{" + PATH_ITEM_ID + "}")
    public ItemDto getItem(@RequestHeader(X_HEADER) final Long userId,
                           @PathVariable(PATH_ITEM_ID) final Long itemId) {
        return itemService.getItem(userId, itemId);
    }

    @PatchMapping("/{" + PATH_ITEM_ID + "}")
    public ItemDto updateItem(@RequestHeader(X_HEADER) final Long userId,
                              @PathVariable(PATH_ITEM_ID) final Long itemId,
                              @RequestBody final ItemDto itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping(PATH_SEARCH)
    public Collection<ItemDto> searchItem(@RequestParam final String text) {
        return itemService.searchItem(text);
    }

}