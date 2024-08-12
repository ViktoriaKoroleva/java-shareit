package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @Size(max = 255)
    @NotEmpty
    private String name;

    @Size(max = 512)
    @NotEmpty
    @Email(message = "Invalid email format")
    private String email;
}
