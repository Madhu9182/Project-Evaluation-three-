package io.capgemini.com.service;

import io.capgemini.com.exception.UserNameNotFoundException;
import io.capgemini.com.exception.WrongPasswordException;
import io.capgemini.com.model.LoginRequest;
import io.capgemini.com.model.LoginResponse;
import io.capgemini.com.model.PasswordUpdate;
import io.capgemini.com.model.UserRegistration;

public interface UserService {

    String saveRegistration(UserRegistration userRegistration);

    String updateRegistration(UserRegistration userRegistration) throws UserNameNotFoundException;

    String updatePassword(String username, PasswordUpdate passwordUpdate) throws UserNameNotFoundException, WrongPasswordException;

    UserRegistration getRegistration(String username) throws UserNameNotFoundException;

    LoginResponse verifyLoginUser(LoginRequest loginRequest) throws UserNameNotFoundException;

    String deleteRegistration(String username) throws UserNameNotFoundException;
}
