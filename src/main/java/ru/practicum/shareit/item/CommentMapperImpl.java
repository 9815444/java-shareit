package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;

@Component
@Mapper
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private UserService userService;

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(comment.getText(), LocalDateTime.now());
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto, Long userId, Long itemId) {
        return new Comment(null, userId, itemId,
                commentDto.getText(), commentDto.getCreated(), userService.findUser(userId).getName());
    }
}
