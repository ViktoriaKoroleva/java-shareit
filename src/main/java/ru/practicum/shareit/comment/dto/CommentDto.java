package ru.practicum.shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validator.ValidateWhile;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private long id;
    @NotBlank(groups = ValidateWhile.Create.class, message = "Комментарий для Item не может быть пустым")
    private String text;
    private long itemId;
    private String authorName;
    private LocalDateTime created;
}
