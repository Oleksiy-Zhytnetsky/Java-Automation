package ua.edu.ukma.Zhytnetsky.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.edu.ukma.Zhytnetsky.annotations.InRange;

public final class InRangeValidator implements ConstraintValidator<InRange, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(InRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return value >= min && value <= max;
    }

}
