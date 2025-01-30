package com.example.echo_api.service.relationship.follow;

import com.example.echo_api.exception.custom.relationship.AlreadyFollowingException;
import com.example.echo_api.exception.custom.relationship.NotFollowingException;
import com.example.echo_api.exception.custom.relationship.SelfActionException;
import com.example.echo_api.persistence.model.profile.Profile;

public interface FollowService {

    public void follow(Profile source, Profile target) throws SelfActionException, AlreadyFollowingException;

    public void unfollow(Profile source, Profile target) throws SelfActionException, NotFollowingException;

    public boolean isFollowing(Profile source, Profile target);

    public boolean isFollowedBy(Profile source, Profile target);

}
