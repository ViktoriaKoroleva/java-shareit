package ru.practicum.shareit.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;

import java.util.List;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class)
public interface CommentListMapper {

    List<Comment> toListModel(List<CommentDto> commentListDto);

    List<CommentDto> toListDto(List<Comment> commentList);

}
