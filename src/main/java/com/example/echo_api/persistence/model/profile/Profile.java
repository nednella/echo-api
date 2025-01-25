package com.example.echo_api.persistence.model.profile;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.validation.annotations.Username;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a {@link User} profile in the system.
 */
@Getter
@NoArgsConstructor
@Entity
@Table
public class Profile {

    // ---- primary keys / foreign keys ----

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private UUID userId; // PK, FK

    @Username
    @Column(unique = true, nullable = false)
    private String username; // FK

    // ---- entity-specific attributes ----

    @Column(name = "display_name")
    private String displayName;

    private String bio;

    private String location;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "following_count")
    private int followingCount = 0; // default = 0

    @Column(name = "follower_count")
    private int followerCount = 0; // default = 0

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ---- constructors ----

    public Profile(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    // ---- setters ----

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    // ---- incrementers ----

    public void incrementFollowing() {
        this.followingCount++;
    }

    public void incrementFollowers() {
        this.followerCount++;
    }

    // ---- decrementers ----

    public void decrementFollowing() {
        this.followingCount--;
    }

    public void decrementFollowers() {
        this.followerCount--;
    }

}
