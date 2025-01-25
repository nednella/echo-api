package com.example.echo_api.service.profile;

import org.springframework.stereotype.Service;

import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile registerForUser(User user) {
        Profile profile = new Profile(user);
        profileRepository.save(profile);

        return profile;
    }

}
