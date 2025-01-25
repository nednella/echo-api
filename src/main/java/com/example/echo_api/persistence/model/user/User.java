package com.example.echo_api.persistence.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.echo_api.validation.annotations.Username;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an account in the system.
 * 
 * <p>
 * This class is mapped to the {@code user} table in the database and stores
 * essential account information required for user authentication and account
 * management in the system.
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

    // ---- primary keys / foreign keys ----

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ---- entity-specific attributes ----

    @Username
    private String username;

    @Column(name = "encrypted_password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // default = Role.USER

    private boolean enabled = true; // default = true

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ---- constructors ----

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ---- setters ----

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // ---- equals & hashcode ----

    /**
     * Compares this {@code User} object with that {@code User} object for equality.
     * 
     * <p>
     * Equality is determined based on the unique {@code username} field, as the
     * unique {@code id} field is only generated once the user is persisted.
     * 
     * @param o the object to be compared
     * @return {@code true} if the username of both users is equal, else
     *         {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;

        User that = (User) o;
        return Objects.equals(this.username, that.username);
    }

    /**
     * Generates a hashcode for this {@code User} object based on the unique
     * username field.
     * 
     * <p>
     * Two equal {@code User} objects will always generate an equal
     * {@code hashCode}, but two equal hashcodes do not guarantee that the
     * {@code User} objects are also equal.
     * 
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.username);
    }

}
