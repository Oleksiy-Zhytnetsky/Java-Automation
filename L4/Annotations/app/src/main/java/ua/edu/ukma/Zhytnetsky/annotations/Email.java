package ua.edu.ukma.Zhytnetsky.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.edu.ukma.Zhytnetsky.annotations.validators.EmailValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "Invalid email address format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
