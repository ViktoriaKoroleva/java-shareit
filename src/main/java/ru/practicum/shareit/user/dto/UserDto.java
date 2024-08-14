package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.validator.ValidationGroups;

@Getter
@Setter
@Builder
public class UserDto {

    @Null(groups = ValidationGroups.Create.class, message = "При создании пользователя id должно быть null.")
    private Long id;

    @NotBlank(groups = ValidationGroups.Create.class, message = "Имя пользователя не может быть пустым")
    private String name;

    @Email(groups = ValidationGroups.Create.class,
            message = "email не соответствует стандарту электронного почтового адреса")
    @Email(groups = ValidationGroups.Update.class,
            message = "email не соответствует стандарту электронного почтового адреса")
    @NotNull(groups = ValidationGroups.Create.class, message = "Email пользователя не может быть пустым")
    private String email;

}