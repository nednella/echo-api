package com.example.echo_api.controller.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileRequest;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.service.profile.ProfileService;
import com.example.echo_api.validation.sequence.ValidationOrder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Validated(ValidationOrder.class)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(ApiConfig.Profile.GET_ME)
    public ResponseEntity<ProfileResponse> getMe() {
        ProfileResponse response = profileService.getMe();
        return ResponseEntity.ok(response);
    }

    @PutMapping(ApiConfig.Profile.UPDATE_ME)
    public ResponseEntity<Void> updateMe(@RequestBody @Valid UpdateProfileRequest request) {
        profileService.updateMe(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ApiConfig.Profile.GET_BY_USERNAME)
    public ResponseEntity<ProfileResponse> getByUsername(@PathVariable String username) {
        ProfileResponse response = profileService.getByUsername(username);
        return ResponseEntity.ok(response);
    }

}
