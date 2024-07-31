package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = "Invalid email format")
    private String email;
}
