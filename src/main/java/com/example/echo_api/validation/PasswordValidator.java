package com.example.echo_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link Password} annotation.
 * 
 * <p>
 * This class ensures that a {@code CharSequence} value meets the criteria
 * specified by the regular expression defined in the {@code @Password}
 * annotation. Null values are considered valid by default.
 * 
 * @see Password
 * @see ConstraintValidator
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private String regexp;

    @Override
    public void initialize(Password constraintAnnotation) {
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
