package ru.practicum.shareit.item.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.item.dto.ItemDto;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    ItemClient itemClient;
    static final String PATH_COMMENT = "comment";
    static final String PATH_ITEM_ID = "item-id";
    static final String PATH_SEARCH = "/search";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PostMapping("/{" + PATH_ITEM_ID + "}/" + PATH_COMMENT)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createComment(@RequestHeader(X_HEADER) @Positive final Long userId,
                                                @PathVariable(PATH_ITEM_ID) @Positive final Long itemId,
                                                @RequestBody @Validated(CreateGroup.class) final CommentDto commentDto) {
        return itemClient.createComment(userId, itemId, commentDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItem(@RequestHeader(X_HEADER) @Positive final Long userId,
                              @RequestBody @Validated(CreateGroup.class) final ItemDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemsFromUser(@RequestHeader(X_HEADER) @Positive final Long userId) {
        return itemClient.getAllItemsFromUser(userId);
    }

    @GetMapping("/{" + PATH_ITEM_ID + "}")
    public ResponseEntity<Object> getItem(@RequestHeader(X_HEADER) @Positive final Long userId,
                                          @PathVariable(PATH_ITEM_ID) @Positive final Long itemId) {
        return itemClient.getItem(userId, itemId);
    }

    @PatchMapping("/{" + PATH_ITEM_ID + "}")
    public ResponseEntity<Object> updateItem(@RequestHeader(X_HEADER) @Positive final Long userId,
                              @PathVariable(PATH_ITEM_ID) @Positive final Long itemId,
                              @RequestBody final ItemDto itemDto) {
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping(PATH_SEARCH)
    public ResponseEntity<Object> searchItem(@RequestHeader(X_HEADER) @Positive final Long userId,
                                             @RequestParam final String text) {
        return itemClient.searchItem(userId, text);
    }

}