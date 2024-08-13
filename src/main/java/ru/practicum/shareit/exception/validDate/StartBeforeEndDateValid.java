package ru.practicum.shareit.exception.validDate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface StartBeforeEndDateValid {
    String message() default "Start must be before end or not null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
