package ru.practicum.shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    String authorName;
    LocalDateTime created;
    Long id;
    @NotBlank(groups = {CreateGroup.class}, message = "Отзыв не может быть пустым.")
    String text;

}