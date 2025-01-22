package com.example.echo_api.validation;

import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link NewPasswordUnique} annotation.
 * 
 * <p>
 * This class ensures that fields {@code newPassword} and
 * {@code currentPassword} do not match within a {@link UpdatePasswordRequest}
 * form.
 * 
 * @see NewPasswordUnique
 * @see ConstraintValidator
 */
public class NewPasswordUniqueValidator implements ConstraintValidator<NewPasswordUnique, UpdatePasswordRequest> {

    @Override
    public boolean isValid(UpdatePasswordRequest form, ConstraintValidatorContext context) {
        return !form.newPassword().equals(form.currentPassword());
    }

}