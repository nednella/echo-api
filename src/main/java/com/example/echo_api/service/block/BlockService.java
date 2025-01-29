package com.example.echo_api.service.block;

import java.util.UUID;

public interface BlockService {

    public void block(UUID source, UUID target);

    public void unblock(UUID source, UUID target);

    public boolean isBlocking(UUID source, UUID target);

    public boolean isBlockedBy(UUID source, UUID target);

}
