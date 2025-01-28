package com.example.echo_api.service.profile;

import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.ProfileRepository;
import com.example.echo_api.service.session.SessionService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of a {@link Profile}.
 * 
 * @see SessionService
 * @see ProfileRepository
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final SessionService sessionService;

    private final ProfileRepository profileRepository;

    @Override
    public Profile registerForUser(User user) {
        Profile profile = new Profile(user);
        return profileRepository.save(profile);
    }

    @Override
    public ProfileResponse getByUsername(String username) throws UsernameException {
        Profile profile = findByUsername(username);
        return ProfileMapper.toResponse(profile);
    }

    @Override
    public ProfileResponse getMe() {
        Profile me = findMe();
        return ProfileMapper.toResponse(me);
    }

    @Override
    public void updateMeProfile(UpdateProfileDTO request) {
        Profile me = findMe();
        ProfileMapper.updateProfile(request, me);
        profileRepository.save(me);
    }

    /**
     * Internal method for obtaining a {@link Profile} via {@code username} from
     * {@link ProfileRepository}.
     * 
     * @param username The username to search within the repository.
     * @return The found {@link Profile}.
     * @throws UsernameException If no profile by that username exists.
     */
    private Profile findByUsername(String username) throws UsernameException {
        return profileRepository.findByUsername(username)
            .orElseThrow(UsernameNotFoundException::new);
    }

    /**
     * Internal method for obtaining a {@link Profile} associated to the
     * authenticated user.
     * 
     * @return The found {@link Profile}.
     */
    private Profile findMe() {
        User me = sessionService.getAuthenticatedUser();
        return findByUsername(me.getUsername());
    }

}
