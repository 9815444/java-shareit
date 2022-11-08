package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

import java.time.LocalDateTime;

public class CommentMapper {

    public static CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(comment.getText(), LocalDateTime.now());
    }

    public static Comment commentDtoToComment(CommentDto commentDto, Long userId, Long itemId) {
        return new Comment(null, userId, itemId,
                commentDto.getText(), commentDto.getCreated(), null);
    }

}
