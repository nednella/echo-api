package com.example.echo_api.service.profile;

import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.ProfileRepository;
import com.example.echo_api.service.session.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final SessionService sessionService;

    private final ProfileRepository profileRepository;

    @Override
    public Profile registerForUser(User user) {
        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return profile;
    }

    @Override
    public ProfileResponse getByUsername(String username) throws UsernameException {
        Profile profile = profileRepository.findByUsername(username)
            .orElseThrow(UsernameNotFoundException::new);

        return ProfileMapper.toResponse(profile);
    }

    @Override
    public ProfileResponse getMe() {
        User me = sessionService.getAuthenticatedUser();
        return getByUsername(me.getUsername());
    }

}
