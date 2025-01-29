package com.example.echo_api.service.follow;

import java.util.UUID;

public interface FollowService {

    public void follow(UUID source, UUID target);

    public void unfollow(UUID source, UUID target);

    public boolean isFollowing(UUID source, UUID target);

    public boolean isFollowedBy(UUID source, UUID target);

}
