package ua.edu.ukma.Zhytnetsky.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ua.edu.ukma.Zhytnetsky.classes.Course;
import ua.edu.ukma.Zhytnetsky.classes.User;

import java.util.Set;

public final class ValidationUtils {

    private static ValidationUtils instance;
    private final Validator validator;

    private ValidationUtils() {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
        if (this.validator == null) {
            throw new IllegalStateException("Error: Validator is null");
        }
    }

    public static synchronized ValidationUtils instance() {
        if (ValidationUtils.instance == null) {
            ValidationUtils.instance = new ValidationUtils();
        }
        return ValidationUtils.instance;
    }

    public void validateUser(final User user) {
        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            displayViolations("user", violations);
            throw new IllegalStateException("Invalid user");
        }
    }

    public void validateCourse(final Course course) {
        final Set<ConstraintViolation<Course>> violations = validator.validate(course);
        if (!violations.isEmpty()) {
            displayViolations("course", violations);
            throw new IllegalStateException("Invalid course");
        }
    }

    private static <T> void displayViolations(final String className, final Set<ConstraintViolation<T>> violations) {
        System.err.println("\n\n<---------------- " + className.toUpperCase() + " CONSTRAINT VIOLATIONS ----------------->");
        for (final ConstraintViolation<T> violation : violations) {
            System.err.println("Violation on field: " + violation.getPropertyPath());
            System.err.println("Violation message: " + violation.getMessage());
            System.err.println("---------------------------------------------------------");
        }
    }

}
