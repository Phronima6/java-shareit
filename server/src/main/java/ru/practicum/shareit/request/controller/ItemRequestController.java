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
    static final String xHeader = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto createItemRequest(@RequestHeader(xHeader) final Long userId,
                                            @RequestBody final ItemRequestDto itemRequestDto) {
        return itemRequestService.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllFromRequestor(@RequestHeader(xHeader) final Long userId) {
        return itemRequestService.getAllFromRequestor(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(@RequestHeader(xHeader) final Long userId,
                                         @PathVariable final Long requestId) {
        return itemRequestService.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getOtherItemRequests(@RequestHeader(xHeader) final Long userId) {
        return itemRequestService.getOtherItemRequests(userId);
    }

}