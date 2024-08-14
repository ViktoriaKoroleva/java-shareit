package ru.practicum.shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validator.ValidationGroups;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private long id;
    @NotBlank(groups = ValidationGroups.Create.class, message = "Комментарий для Item не может быть пустым")
    private String text;
    private long itemId;
    private String authorName;
    private LocalDateTime created;
}
