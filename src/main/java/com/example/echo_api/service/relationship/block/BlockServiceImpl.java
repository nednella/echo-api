package com.example.echo_api.service.relationship.block;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.exception.custom.relationship.AlreadyBlockingException;
import com.example.echo_api.exception.custom.relationship.NotBlockingException;
import com.example.echo_api.exception.custom.relationship.SelfActionException;
import com.example.echo_api.persistence.model.block.Block;
import com.example.echo_api.persistence.repository.BlockRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRD operations of {@link Block}
 * relationships.
 * 
 * @see Block
 * @see BlockRepository
 */
@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

    @Override
    public void block(UUID source, UUID target) throws SelfActionException, AlreadyBlockingException {
        if (isSelfAction(source, target)) {
            throw new SelfActionException(ErrorMessageConfig.SELF_BLOCK);
        }
        if (isBlocking(source, target)) {
            throw new AlreadyBlockingException();
        }

        blockRepository.save(new Block(source, target));
    }

    @Override
    public void unblock(UUID source, UUID target) throws SelfActionException, NotBlockingException {
        if (isSelfAction(source, target)) {
            throw new SelfActionException(ErrorMessageConfig.SELF_UNBLOCK);
        }
        if (!isBlocking(source, target)) {
            throw new NotBlockingException();
        }

        blockRepository.delete(new Block(source, target));
    }

    @Override
    public boolean isBlocking(UUID source, UUID target) {
        return blockRepository.existsByBlockerIdAndBlockingId(source, target);
    }

    @Override
    public boolean isBlockedBy(UUID source, UUID target) {
        return isBlocking(target, source);
    }

    /**
     * Internal method for checking if the UUIDs match.
     * 
     * @param source The source {@link UUID}.
     * @param target The target {@link UUID}.
     * @return Boolean indicating whether the UUIDs are a match.
     */
    private boolean isSelfAction(UUID source, UUID target) {
        return Objects.equals(source, target);
    }

}
