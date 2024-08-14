package ru.practicum.shareit.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    Comment toModel(CommentDto commentDto);

    CommentDto toDto(Comment comment);

}
