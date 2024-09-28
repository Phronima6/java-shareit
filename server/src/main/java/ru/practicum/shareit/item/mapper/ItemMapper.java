package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {

    public ItemDto toItemDto(final Item item) {
        final ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(item.getAvailable());
        itemDto.setDescription(item.getDescription());
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setRequestId(item.getId());
        return itemDto;
    }

    public Item toItem(final ItemDto itemDto) {
        final Item item = new Item();
        item.setAvailable(itemDto.getAvailable());
        item.setDescription(itemDto.getDescription());
        item.setName(itemDto.getName());
        return item;
    }

}