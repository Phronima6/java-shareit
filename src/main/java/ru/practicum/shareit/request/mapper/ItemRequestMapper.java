package ru.practicum.shareit.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

@Component
public class ItemRequestMapper {

    public ItemRequestDto toItemRequestDto(final ItemRequest itemRequest) {
        final ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setRequestor(itemRequest.getRequestor());
        return itemRequestDto;
    }

    public ItemRequest toItemRequest(final ItemRequestDto itemRequestDto) {
        final ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(itemRequestDto.getCreated());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setRequestor(itemRequestDto.getRequestor());
        return itemRequest;
    }

}