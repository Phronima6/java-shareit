package ru.practicum.shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    @NotBlank(groups = {CreateGroup.class}, message = "Отзыв не может быть пустым.")
    String text;

}