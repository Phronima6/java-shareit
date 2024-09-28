package ru.practicum.shareit.comment.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(final Comment comment) {
        final CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setCreated(comment.getCreated());
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    public Comment toComment(final CommentDto commentDto) {
        final Comment comment = new Comment();
        comment.setText(commentDto.getText());
        return comment;
    }

}