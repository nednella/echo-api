package com.example.echo_api.service.profile;

import org.springframework.stereotype.Service;

import com.example.echo_api.persistence.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

}
