package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.group.CreateGroup;
import java.util.Collection;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {

    @NotNull(groups = {CreateGroup.class}, message = "Поле доступность вещи не может быть пустым.")
    Boolean available;
    Collection<CommentDto> comments;
    @NotBlank(groups = {CreateGroup.class}, message = "Описание вещи не может быть пустым.")
    String description;
    Long id;
    BookingDtoOutput lastBooking;
    @NotBlank(groups = {CreateGroup.class}, message = "Электронная почта пользователя не может быть пустой.")
    String name;
    BookingDtoOutput nextBooking;

}