package com.example.echo_api.service.profile;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;

public interface ProfileService {

    /**
     * Creates a profile for a newly registered {@link User} with a 1-to-1
     * relationship. The method is consumed by {@link AccountService}.
     * 
     * @param user the user for whom the profile is to be created.
     * @return The created {@link Profile}.
     */
    public Profile registerForUser(User user);

    /**
     * Fetch a {@link Profile} by username to return to the client.
     * 
     * @param username The username of the profile to fetch.
     * @return A {@link ProfileDTO} resembling the profile.
     * @throws UsernameException If the username is not found.
     */
    public ProfileDTO getByUsername(String username) throws UsernameException;

    /**
     * Fetches the {@link Profile} of the authenticated user to return to the
     * client.
     * 
     * @return A {@link ProfileDTO} resembling the profile.
     */
    public ProfileDTO getMe();

    /**
     * Updates the profile information of the authenticated user.
     * 
     * <p>
     * Valid fields are {@code name}, {@code bio}, {@code location}.
     * 
     * @param request The {@link UpdateProfileDTO} request containing the updated
     *                profile information.
     */
    public void updateMeProfile(UpdateProfileDTO request);

}
