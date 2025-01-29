package com.example.echo_api.service.relationship.block;

import java.util.UUID;

import com.example.echo_api.exception.custom.relationship.AlreadyBlockingException;
import com.example.echo_api.exception.custom.relationship.NotBlockingException;
import com.example.echo_api.exception.custom.relationship.SelfActionException;

public interface BlockService {

    public void block(UUID source, UUID target) throws SelfActionException, AlreadyBlockingException;

    public void unblock(UUID source, UUID target) throws SelfActionException, NotBlockingException;

    public boolean isBlocking(UUID source, UUID target);

    public boolean isBlockedBy(UUID source, UUID target);

}
