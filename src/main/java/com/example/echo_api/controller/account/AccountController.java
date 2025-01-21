package com.example.echo_api.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.dto.request.account.UpdateUsernameRequest;
import com.example.echo_api.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    // @formatter:off
    @GetMapping(ApiConfig.Account.USERNAME_AVAILABLE)
    public ResponseEntity<Boolean> isUsernameAvailable(
        @RequestParam("username")
        @Valid
        UpdateUsernameRequest username
    ) {
        return ResponseEntity.ok(!userService.existsByUsername(username.username()));
    }

    @PutMapping(ApiConfig.Account.UPDATE_USERNAME)
    public ResponseEntity<Void> updateUsername(
        @RequestParam("username")
        @Valid
        UpdateUsernameRequest username
    ) {
        userService.updateUsername(username.username());
        return ResponseEntity.noContent().build();
    }
    // @formatter:on

    @PutMapping(ApiConfig.Account.UPDATE_PASSWORD)
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.noContent().build();
    }

}
