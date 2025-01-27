package com.example.echo_api.service.profile;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileInfoRequest;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
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
     * @return A {@link ProfileResponse} resembling the profile.
     * @throws UsernameException If the username is not found.
     */
    public ProfileResponse getByUsername(String username) throws UsernameException;

    /**
     * Fetches the {@link Profile} of the authenticated user to return to the
     * client.
     * 
     * @return A {@link ProfileResponse} resembling the profile.
     */
    public ProfileResponse getMe();

    /**
     * Updates the profile information of the authenticated user.
     * 
     * @param request The request containing the updated profile information.
     */
    public void updateMeProfileInfo(UpdateProfileInfoRequest request);

}
