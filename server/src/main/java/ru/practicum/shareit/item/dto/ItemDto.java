package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.comment.dto.CommentDto;
import java.util.Collection;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {

    Boolean available;
    Collection<CommentDto> comments;
    String description;
    Long id;
    BookingDtoOutput lastBooking;
    String name;
    BookingDtoOutput nextBooking;
    Long requestId;

}