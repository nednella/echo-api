package com.example.echo_api.service.auth;

import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.persistence.dto.request.SignInRequest;
import com.example.echo_api.persistence.dto.request.SignUpReqest;

public interface AuthService {

    public void signIn(SignInRequest signInRequest) throws UserNotFoundException;

    public void signUp(SignUpReqest signUpRequest) throws UserAlreadyExistsException;

}
