package com.example.echo_api.service.relationship.block;

import com.example.echo_api.exception.custom.relationship.AlreadyBlockingException;
import com.example.echo_api.exception.custom.relationship.NotBlockingException;
import com.example.echo_api.exception.custom.relationship.SelfActionException;
import com.example.echo_api.persistence.model.profile.Profile;

public interface BlockService {

    public void block(Profile source, Profile target) throws SelfActionException, AlreadyBlockingException;

    public void unblock(Profile source, Profile target) throws SelfActionException, NotBlockingException;

    public boolean isBlocking(Profile source, Profile target);

    public boolean isBlockedBy(Profile source, Profile target);

}
