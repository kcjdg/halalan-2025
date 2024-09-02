package ph.dgtech.halalan.voter.profile.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MinAgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "Age must be at least {value} years";
    int value(); // Minimum age value
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
