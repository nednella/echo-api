package com.example.echo_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link Username} annotation.
 * 
 * <p>
 * This class ensures that a {@code CharSequence} value meets the criteria
 * specified by the regular expression defined in the {@code @Username}
 * annotation. Null values are considered valid by default.
 * 
 * @see Username
 * @see ConstraintValidator
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private String regexp;

    @Override
    public void initialize(Username constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are valid
        }

        return value.matches(regexp);
    }

}