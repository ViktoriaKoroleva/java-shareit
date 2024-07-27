package ru.practicum.shareit.user;

import lombok.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@RequiredArgsConstructor
public class User {
    Long id;
    String name;
    String email;
}
