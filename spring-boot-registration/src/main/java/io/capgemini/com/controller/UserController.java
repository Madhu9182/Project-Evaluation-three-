package io.capgemini.com.controller;

import io.capgemini.com.exception.UserNameNotFoundException;
import io.capgemini.com.exception.WrongPasswordException;
import io.capgemini.com.model.LoginRequest;
import io.capgemini.com.model.LoginResponse;
import io.capgemini.com.model.PasswordUpdate;
import io.capgemini.com.model.UserRegistration;
import io.capgemini.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registrationform")
    public String saveRegistration(@Valid @RequestBody UserRegistration userRegistration) {
        return userService.saveRegistration(userRegistration);
    }

    @PutMapping("/update")
    public String updateRegistration(@Valid @RequestBody UserRegistration userRegistration) throws UserNameNotFoundException {
        return userService.updateRegistration(userRegistration);
    }

    @PutMapping("/updatePassword/{username}")
    public String updatePassword(@PathVariable("username") String username, @RequestBody PasswordUpdate passwordUpdate) throws UserNameNotFoundException, WrongPasswordException {
        return userService.updatePassword(username, passwordUpdate);
    }

    @GetMapping("/registrationform/{username}")
    public UserRegistration getRegistration(@Valid @PathVariable("username") String username) throws UserNameNotFoundException {
        return userService.getRegistration(username);
    }

    @PostMapping("/login")
    public LoginResponse verifyLoginUser(@RequestBody LoginRequest loginRequest) throws UserNameNotFoundException {
        return userService.verifyLoginUser(loginRequest);
    }

    @DeleteMapping("/delete/{username}")
    public String deleteRegistration(@Valid @PathVariable("username") String username) throws UserNameNotFoundException {
        return userService.deleteRegistration(username);
    }
}
