package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.Validation.CreateObject;
import ru.practicum.shareit.Validation.UpdateObject;

@Data
public class UserDto {
    Long id;
    @NotBlank(groups = {CreateObject.class}, message = "Имя не может быть пустым.")
    String name;
    @NotBlank(groups = {CreateObject.class}, message = "Адрес электронной почты UserDTO не может быть пустым.")
    @Email(groups = {CreateObject.class, UpdateObject.class}, message = "Почта должна быть почтой.")
    String email;
}
