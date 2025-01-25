package com.example.echo_api.service.profile;

import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;

public interface ProfileService {

    public Profile registerForUser(User user);

}
