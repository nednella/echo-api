package com.example.echo_api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a User in the system.
 * 
 * <p>
 * This class is mapped to the {@code user} table in the database and
 * stores essential account information required for user authentication and
 * account management in the system.
 *
 * <p>
 * Fields {@code id}, {@code createdAt}, and {@code updatedAt} are
 * auto-generated and managed by Hibernate.
 * 
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Unique identifier */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** Unique username */
    @Column
    private String username;

    /** Hashed password */
    @Column
    private String password;

    /** User role related to permissions */
    @Column
    private String role;

    /** Enabled status */
    @Column
    private boolean enabled = true; // default = true

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {

    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
