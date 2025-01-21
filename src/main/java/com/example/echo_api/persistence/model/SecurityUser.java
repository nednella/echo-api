package com.example.echo_api.persistence.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

/**
 * The {@code SecurityUser} class extends the default Spring Security
 * authentication model by implementing both {@link UserDetails}.
 * 
 * <p>
 * Instances of this class are created with a {@link User} object.
 * 
 * <p>
 * This class acts as a wrapper for the {@link User} in the Spring Security
 * authentication flow, representing an authenticated user retrieved from the
 * database.
 * 
 */
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
