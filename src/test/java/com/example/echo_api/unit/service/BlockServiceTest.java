package com.example.echo_api.unit.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.repository.BlockRepository;
import com.example.echo_api.service.relationship.block.BlockService;
import com.example.echo_api.service.relationship.block.BlockServiceImpl;

/**
 * Unit test class for {@link BlockService}.
 */
@ExtendWith(MockitoExtension.class)
class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BlockServiceImpl blockService;

    private static Profile source;
    private static Profile target;

    @BeforeAll
    static void setup() {
        Account sourceAcc = new Account("source", "test");
        source = new Profile(sourceAcc);

        Account targetAcc = new Account("target", "test");
        target = new Profile(targetAcc);
    }

    @Test
    void BlockService_Block_ReturnVoid() {

    }

    @Test
    void BlockService_Block_ThrowSelfActionException() {

    }

    @Test
    void BlockService_Block_ThrowAlreadyBlockingException() {

    }

    @Test
    void BlockService_Unblock_ReturnVoid() {

    }

    @Test
    void BlockService_Unblock_ThrowSelfActionException() {

    }

    @Test
    void BlockService_Unblock_ThrowNotBlockingException() {

    }

    @Test
    void BlockService_IsBlocking_ReturnTrue() {

    }

    @Test
    void BlockService_IsBlocking_ReturnFalse() {

    }

    @Test
    void BlockService_IsBlockedBy_ReturnTrue() {

    }

    @Test
    void BlockService_IsBlockedBy_ReturnFalse() {

    }

}
