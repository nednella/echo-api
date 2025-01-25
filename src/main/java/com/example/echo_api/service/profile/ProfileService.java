package com.example.echo_api.service.profile;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;

public interface ProfileService {

    public Profile registerForUser(User user);

    public ProfileResponse getByUsername(String username) throws UsernameException;

    public ProfileResponse getMe();

}
