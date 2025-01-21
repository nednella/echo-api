package com.example.echo_api.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.dto.request.account.UpdateUsernameRequest;
import com.example.echo_api.service.account.AccountService;

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

    private final AccountService accountService;

    // @formatter:off
    @GetMapping(ApiConfig.Account.USERNAME_AVAILABLE)
    public ResponseEntity<Boolean> isUsernameAvailable(
        @RequestParam("username")
        @Valid
        UpdateUsernameRequest username
    ) {
        return ResponseEntity.ok(!accountService.existsByUsername(username.username()));
    }

    @PutMapping(ApiConfig.Account.UPDATE_USERNAME)
    public ResponseEntity<Void> updateUsername(
        @RequestParam("username")
        @Valid
        UpdateUsernameRequest username
    ) {
        accountService.updateUsername(username.username());
        return ResponseEntity.noContent().build();
    }
    // @formatter:on

    @PutMapping(ApiConfig.Account.UPDATE_PASSWORD)
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
        accountService.updatePassword(request);
        return ResponseEntity.noContent().build();
    }

}
