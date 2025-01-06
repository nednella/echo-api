package com.example.echo_api.service.auth;

import com.example.echo_api.api.v1.auth.request.SignInRequest;
import com.example.echo_api.api.v1.auth.request.SignUpReqest;
import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;

public interface AuthService {

    public void signIn(SignInRequest signInRequest) throws UserNotFoundException;

    public void signUp(SignUpReqest signUpRequest) throws UserAlreadyExistsException;

}
