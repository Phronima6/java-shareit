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
        return itemRequestDto;
    }

    public ItemRequest toItemRequest(final ItemRequestDto itemRequestDto) {
        final ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        return itemRequest;
    }

}