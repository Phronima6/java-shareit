package ru.practicum.shareit.comment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    String authorName;
    LocalDateTime created;
    Long id;
    String text;

}