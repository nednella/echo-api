package com.example.echo_api.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.example.echo_api.config.ErrorMessageConfig;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Custom Jakarta Validation annotation for {@link UpdatePasswordRequest} forms.
 * 
 * <p>
 * The annotated {@link UpdatePasswordRequest} record fields {@code newPassword}
 * and {@code currentPassword} must not match.
 * 
 * <p>
 * Accepts type annotations for {@link UpdatePasswordRequest}.
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NewPasswordUniqueValidator.class)
public @interface NewPasswordUnique {

    /**
     * @return the error message template
     */
    String message() default ErrorMessageConfig.NEW_PASSWORD_NOT_UNIQUE;

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};

}