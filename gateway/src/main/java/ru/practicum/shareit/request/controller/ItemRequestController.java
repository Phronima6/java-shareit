package ru.practicum.shareit.request.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    ItemRequestClient itemRequestClient;
    static final String xHeader = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItemRequest(@RequestHeader(xHeader) @Positive final Long userId,
                @RequestBody @Validated(CreateGroup.class) final ItemRequestDto itemRequestDto) {
        return itemRequestClient.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllFromRequestor(@RequestHeader(xHeader) @Positive final Long userId) {
        return itemRequestClient.getAllFromRequestor(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader(xHeader) @Positive final Long userId,
                                         @PathVariable @Positive final Long requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getOtherItemRequests(@RequestHeader(xHeader) @Positive final Long userId) {
        return itemRequestClient.getOtherItemRequests(userId);
    }

}