package com.example.echo_api.unit.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.repository.FollowRepository;
import com.example.echo_api.service.metrics.MetricsService;
import com.example.echo_api.service.relationship.follow.FollowServiceImpl;

/**
 * Unit test class for {@link FollowService}.
 */
@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private MetricsService metricsService;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowServiceImpl followService;

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
    void FollowService_Follow_ReturnVoid() {

    }

    @Test
    void FollowService_Follow_ThrowSelfActionException() {

    }

    @Test
    void FollowService_Follow_ThrowAlreadyFollowingException() {

    }

    @Test
    void FollowService_Unfollow_ReturnVoid() {

    }

    @Test
    void FollowService_Unfollow_ThrowSelfActionException() {

    }

    @Test
    void FollowService_Unfollow_ThrowNotFollowingException() {

    }

    @Test
    void FollowService_IsFollowing_ReturnTrue() {

    }

    @Test
    void FollowService_IsFollowing_ReturnFalse() {

    }

    @Test
    void FollowService_IsFollowedBy_ReturnTrue() {

    }

    @Test
    void FollowService_IsFollowedBy_ReturnFalse() {

    }

}
