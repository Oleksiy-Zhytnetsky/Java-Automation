package ua.edu.ukma.Zhytnetsky.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.edu.ukma.Zhytnetsky.annotations.validators.InRangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InRangeValidator.class)
public @interface InRange {

    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;

    String message() default "Value out of range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
