package com.example.echo_api.validation.account.validators;

import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.validation.account.annotations.ConfirmationPasswordMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link ConfirmationPasswordMatch}
 * annotation.
 * 
 * <p>
 * This class ensures that fields {@code confirmationPassword} and
 * {@code newPassword} match exactly within a {@link UpdatePasswordRequest}
 * form.
 * 
 * @see ConfirmationPasswordMatch
 * @see ConstraintValidator
 */
public class ConfirmationPasswordMatchValidator
    implements ConstraintValidator<ConfirmationPasswordMatch, UpdatePasswordRequest> {

    @Override
    public boolean isValid(UpdatePasswordRequest form, ConstraintValidatorContext context) {
        return form.confirmationPassword().equals(form.newPassword());
    }

}
