package ru.practicum.shareit.request.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    ItemRequestService itemRequestService;
    static final String PATH_ALL = "/all";
    static final String PATH_REQUEST_ID = "request-id";
    static final String X_HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto createItemRequest(@RequestHeader(X_HEADER) final Long userId,
                                            @RequestBody final ItemRequestDto itemRequestDto) {
        return itemRequestService.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllFromRequestor(@RequestHeader(X_HEADER) final Long userId) {
        return itemRequestService.getAllFromRequestor(userId);
    }

    @GetMapping("/{" + PATH_REQUEST_ID + "}")
    public ItemRequestDto getItemRequest(@RequestHeader(X_HEADER) final Long userId,
                                         @PathVariable(PATH_REQUEST_ID) final Long requestId) {
        return itemRequestService.getItemRequest(userId, requestId);
    }

    @GetMapping(PATH_ALL)
    public Collection<ItemRequestDto> getOtherItemRequests(@RequestHeader(X_HEADER) final Long userId) {
        return itemRequestService.getOtherItemRequests(userId);
    }

}