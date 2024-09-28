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
    static final String xHeader = "X-Sharer-User-Id";

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createComment(@RequestHeader(xHeader) @Positive final Long userId,
                                                @PathVariable @Positive final Long itemId,
                                                @RequestBody @Validated(CreateGroup.class) final CommentDto commentDto) {
        return itemClient.createComment(userId, itemId, commentDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItem(@RequestHeader(xHeader) @Positive final Long userId,
                              @RequestBody @Validated(CreateGroup.class) final ItemDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemsFromUser(@RequestHeader(xHeader) @Positive final Long userId) {
        return itemClient.getAllItemsFromUser(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader(xHeader) @Positive final Long userId,
                                          @PathVariable @Positive final Long itemId) {
        return itemClient.getItem(userId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(xHeader) @Positive final Long userId,
                              @PathVariable @Positive final Long itemId,
                              @RequestBody final ItemDto itemDto) {
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestHeader(xHeader) @Positive final Long userId,
                                             @RequestParam final String text) {
        return itemClient.searchItem(userId, text);
    }

}