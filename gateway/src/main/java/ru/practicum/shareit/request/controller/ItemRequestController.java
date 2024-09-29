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
    static final String PATH_ALL = "/all";
    static final String PATH_REQUEST_ID = "request-id";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItemRequest(@RequestHeader(X_HEADER) @Positive final Long userId,
                @RequestBody @Validated(CreateGroup.class) final ItemRequestDto itemRequestDto) {
        return itemRequestClient.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllFromRequestor(@RequestHeader(X_HEADER) @Positive final Long userId) {
        return itemRequestClient.getAllFromRequestor(userId);
    }

    @GetMapping("/{" + PATH_REQUEST_ID + "}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader(X_HEADER) @Positive final Long userId,
                                         @PathVariable(PATH_REQUEST_ID) @Positive final Long requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @GetMapping(PATH_ALL)
    public ResponseEntity<Object> getOtherItemRequests(@RequestHeader(X_HEADER) @Positive final Long userId) {
        return itemRequestClient.getOtherItemRequests(userId);
    }

}