package com.example.echo_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import lombok.Setter;

/**
 * Configuration class for Spring Session.
 * 
 * Congifures Redis-backed HTTP session management for the application for
 * dsitributed session management.
 * 
 * For more information, refer to:
 * {@link https://docs.spring.io/spring-session/reference/configuration/redis.html}.
 * 
 * * If interested in implementing custom logout handling such as logout
 * everywhere, see:
 * {@link https://docs.spring.io/spring-session/reference/configuration/redis.html#finding-all-user-sessions}.
 * 
 */
@Setter
@Configuration
@EnableRedisHttpSession
@ConfigurationProperties(prefix = "spring.data.redis")
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {

    private String host;
    private int port;

    /**
     * Configures the Redis connection factory using Lettuce. This factory creates
     * Redis connections used for session management.
     * 
     * @return LettuceConnectionFactory for Redis connections
     */
    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * Customises the session cookie.
     * 
     * @return CookieSerializer
     */
    @Bean
    CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("ECHO_SESSION");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    /**
     * Configures the SecurityContextRepository to store security contexts in HTTP
     * sessions managed by Redis for persistence.
     * 
     * @return HttpSessionSecurityContextRepository
     */
    @Bean
    SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

}
