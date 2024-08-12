package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @Size(max = 255)
    private String name;
    @Size(max = 512)
    private String email;
}

