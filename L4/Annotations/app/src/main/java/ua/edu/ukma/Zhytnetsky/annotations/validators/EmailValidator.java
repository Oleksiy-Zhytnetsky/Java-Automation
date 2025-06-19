package ua.edu.ukma.Zhytnetsky.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.edu.ukma.Zhytnetsky.annotations.Email;

import java.util.regex.Pattern;

public final class EmailValidator implements ConstraintValidator<Email, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }

}
