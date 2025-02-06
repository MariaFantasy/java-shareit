package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentDtoMapper {

    public Comment mapFromDto(Item item, User user, CommentRequestDto commentRequestDto, LocalDateTime created) {
        final Comment comment = new Comment(
                null,
                item,
                user,
                commentRequestDto.getText(),
                created
        );
        return comment;
    }

    public Comment mapFromDto(Item item, User user, CommentResponseDto commentResponseDto) {
        final Comment comment = new Comment(
                commentResponseDto.getId(),
                item,
                user,
                commentResponseDto.getText(),
                commentResponseDto.getCreated()
        );
        return comment;
    }

    public CommentResponseDto mapToResponseDto(Comment comment) {
        final CommentResponseDto commentDto = new CommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getUser().getName(),
                comment.getCreated()
        );
        return commentDto;
    }
}
