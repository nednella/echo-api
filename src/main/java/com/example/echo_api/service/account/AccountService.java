package com.example.echo_api.service.account;

import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.model.account.Role;
import com.example.echo_api.persistence.model.account.Account;

public interface AccountService {

    /**
     * Creates a new {@link Account} within the application, using the standard
     * {@link Role}, {@code Role.USER}.
     * 
     * @param username The username of the account to register.
     * @param password The password of the account to register.
     * @return The newly registered {@link Account}.
     * @throws UsernameAlreadyExistsException If the username is already taken.
     */
    public Account register(String username, String password) throws UsernameAlreadyExistsException;

    /**
     * Creates a new {@link Account} within the application, using the supplied
     * {@link Role}.
     * 
     * @param username The username of the account to register.
     * @param password The password of the account to register.
     * @return The newly registered {@link Account}.
     * @throws UsernameAlreadyExistsException If the username is already taken.
     */
    public Account registerWithRole(String username, String password, Role role) throws UsernameAlreadyExistsException;

    /**
     * Returns a boolean indicating whether the supplied {@code username} is
     * available.
     * 
     * @param username The username to check for availability.
     * @return A boolean indicating the availability.
     */
    public boolean isUsernameAvailable(String username);

    /**
     * Updates the authenticated user's username.
     * 
     * @param username The new username for the authenticated user.
     * @throws UsernameAlreadyExistsException If the username is already taken.
     */
    public void updateUsername(String username) throws UsernameAlreadyExistsException;

    /**
     * Update the authenticated user's password.
     * 
     * @param currentPassword The current password of the authenticated user.
     * @param newPassword     The new password for the authenticated user.
     * @throws IncorrectCurrentPasswordException If the supplied
     *                                           {@code currentPassword} does not
     *                                           match the authenticated user's
     *                                           existing password.
     */
    public void updatePassword(String currentPassword, String newPassword) throws IncorrectCurrentPasswordException;

}
