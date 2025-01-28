package com.example.echo_api.persistence.model.profile;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.echo_api.persistence.model.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a {@link Account} profile in the system.
 */
@Entity
@Table(name = "profile_metrics")
@Getter
@NoArgsConstructor
public class Metrics {

    // ---- primary keys / foreign keys ----

    @Id
    @Column(name = "profile_id", unique = true, nullable = false)
    private UUID profileId; // PK, FK

    // ---- entity-specific attributes ----

    @Column(name = "following_count")
    private int followingCount = 0;

    @Column(name = "follower_count")
    private int followerCount = 0;

    @Column(name = "post_count")
    private int postCount = 0;

    @Column(name = "media_count")
    private int mediaCount = 0;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ---- constructors ----

    public Metrics(Profile profile) {
        this.profileId = profile.getProfileId();
    }

    // ---- incrementers ----

    public void incrementFollowing() {
        this.followingCount++;
    }

    public void incrementFollowers() {
        this.followerCount++;
    }

    public void incrementPostCount() {
        this.postCount++;
    }

    public void incrementMediaCount() {
        this.mediaCount++;
    }

    // ---- decrementers ----

    public void decrementFollowing() {
        this.followingCount--;
    }

    public void decrementFollowers() {
        this.followerCount--;
    }

    public void decrementPostCount() {
        this.postCount--;
    }

    public void decrementMediaCount() {
        this.mediaCount--;
    }

}
