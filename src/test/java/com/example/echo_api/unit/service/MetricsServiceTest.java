package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.echo_api.exception.custom.account.IdNotFoundException;
import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Metrics;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.repository.MetricsRepository;
import com.example.echo_api.service.metrics.MetricsServiceImpl;

/**
 * Unit test class for {@link MetricsServiceTest}.
 */
@ExtendWith(MockitoExtension.class)
class MetricsServiceTest {

    @Mock
    private MetricsRepository metricsRepository;

    @InjectMocks
    private MetricsServiceImpl metricsService;

    private static Metrics expected;

    @BeforeAll
    static void setup() {
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        expected = new Metrics(profile);
    }

    @Test
    void MetricsService_GetMetrics_ReturnMetrics() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act
        Metrics actual = metricsService.getMetrics(uuid);

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_GetMetrics_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.getMetrics(uuid));
        verify(metricsRepository, times(1)).findById(uuid);

    }

    // following

    @Test
    void MetricsService_IncrementFollowing_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.incrementFollowing(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_IncrementFollowing_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.incrementFollowing(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementFollowing_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.decrementFollowing(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementFollowing_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.decrementFollowing(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    // followers

    @Test
    void MetricsService_IncrementFollowers_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.incrementFollowers(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_IncrementFollowers_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.incrementFollowers(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementFollowers_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.decrementFollowers(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementFollowers_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.decrementFollowers(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    // posts

    @Test
    void MetricsService_IncrementPosts_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.incrementPosts(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_IncrementPosts_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.incrementPosts(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementPosts_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.decrementPosts(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementPosts_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.decrementPosts(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    // media

    @Test
    void MetricsService_IncrementMedia_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.incrementMedia(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_IncrementMedia_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.incrementMedia(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementMedia_ReturnVoid() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.of(expected));

        // act & assert
        assertDoesNotThrow(() -> metricsService.decrementMedia(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

    @Test
    void MetricsService_DecrementMedia_ThrowIdNotFound() {
        // arrange
        UUID uuid = UUID.randomUUID();
        when(metricsRepository.findById(uuid)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(IdNotFoundException.class, () -> metricsService.decrementMedia(uuid));
        verify(metricsRepository, times(1)).findById(uuid);
    }

}
