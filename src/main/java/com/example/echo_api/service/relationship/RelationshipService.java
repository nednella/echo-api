package com.example.echo_api.service.relationship;

import com.example.echo_api.persistence.dto.response.profile.RelationshipDTO;
import com.example.echo_api.persistence.model.profile.Profile;

public interface RelationshipService {

    public RelationshipDTO getRelationship(Profile source, Profile target);

    public void follow(Profile source, Profile target);

    public void unfollow(Profile source, Profile target);

    public boolean isFollowing(Profile source, Profile target);

    public boolean isFollowedBy(Profile source, Profile target);

}
