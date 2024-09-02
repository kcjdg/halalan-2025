package ph.dgtech.halalan.voter.profile.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int minAge;


    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext constraintValidatorContext) {
        if (birthday == null) {
            return true; // `@NotNull` should handle null cases
        }
        // Calculate the age based on the birthday
        return Period.between(birthday, LocalDate.now()).getYears() >= minAge;
    }

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }
}
