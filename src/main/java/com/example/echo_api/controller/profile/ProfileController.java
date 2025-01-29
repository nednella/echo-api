package com.example.echo_api.controller.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;
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

    @GetMapping(ApiConfig.Profile.ME)
    public ResponseEntity<ProfileDTO> getMe() {
        ProfileDTO response = profileService.getMe();
        return ResponseEntity.ok(response);
    }

    @PutMapping(ApiConfig.Profile.ME)
    public ResponseEntity<Void> updateMeProfile(@RequestBody @Valid UpdateProfileDTO request) {
        profileService.updateMeProfile(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ApiConfig.Profile.GET_BY_USERNAME)
    public ResponseEntity<ProfileDTO> getByUsername(@PathVariable("username") String username) {
        ProfileDTO response = profileService.getByUsername(username);
        return ResponseEntity.ok(response);
    }

}
