package com.example.echo_api.service.socialcontext;

import com.example.echo_api.persistence.dto.response.profile.SocialContextDTO;
import com.example.echo_api.persistence.model.profile.Profile;

public interface SocialContextService {

    public SocialContextDTO getSocialContext(Profile source, Profile target);

    public void follow(Profile source, Profile target);

    public void unfollow(Profile source, Profile target);

    public boolean isFollowing(Profile source, Profile target);

    public boolean isFollowedBy(Profile source, Profile target);

}
