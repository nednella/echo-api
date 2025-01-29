package com.example.echo_api.service.relationship.follow;

import java.util.UUID;

import com.example.echo_api.exception.custom.relationship.AlreadyFollowingException;
import com.example.echo_api.exception.custom.relationship.NotFollowingException;
import com.example.echo_api.exception.custom.relationship.SelfActionException;

public interface FollowService {

    public void follow(UUID source, UUID target) throws SelfActionException, AlreadyFollowingException;

    public void unfollow(UUID source, UUID target) throws SelfActionException, NotFollowingException;

    public boolean isFollowing(UUID source, UUID target);

    public boolean isFollowedBy(UUID source, UUID target);

}
