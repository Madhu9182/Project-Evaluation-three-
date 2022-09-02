package io.capgemini.com.service;

import io.capgemini.com.exception.UserNameNotFoundException;
import io.capgemini.com.exception.WrongPasswordException;
import io.capgemini.com.model.LoginRequest;
import io.capgemini.com.model.LoginResponse;
import io.capgemini.com.model.PasswordUpdate;
import io.capgemini.com.model.UserRegistration;
import io.capgemini.com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveRegistration(UserRegistration userRegistration) {
        userRegistration.setPassword(encoder.encode(userRegistration.getPassword()));
        userRepository.save(userRegistration);
        return "User Registration Successfully";
    }

    @Override
    public String updateRegistration(UserRegistration userRegistration) throws UserNameNotFoundException {
        UserRegistration registration =  userRepository.findByUsername(userRegistration.getUsername()).orElseThrow(()-> new UserNameNotFoundException());
        registration.setFirstname(userRegistration.getFirstname());
        registration.setLastname(userRegistration.getLastname());
        registration.setEmail(userRegistration.getEmail());
        userRepository.save(registration);
        return "Updated successfully";
    }

    @Override
    public String updatePassword(String username, PasswordUpdate passwordUpdate) throws UserNameNotFoundException, WrongPasswordException {
        UserRegistration registration =  userRepository.findByUsername(username).orElseThrow(()-> new UserNameNotFoundException());
        if(encoder.matches(passwordUpdate.getOldPassword(),registration.getPassword()))
        {
            registration.setPassword(encoder.encode(passwordUpdate.getNewPassword()));
            userRepository.save(registration);
        }else{
            throw new WrongPasswordException();
        }
        return "Password updated Successfully";
    }

    @Override
    public UserRegistration getRegistration(String username) throws UserNameNotFoundException {
        UserRegistration registration =  userRepository.findByUsername(username).orElseThrow(()-> new UserNameNotFoundException());
        return registration;
    }

    @Override
    public LoginResponse verifyLoginUser(LoginRequest loginRequest) throws UserNameNotFoundException {
        LoginResponse response = new LoginResponse();
        UserRegistration registration =  userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new UserNameNotFoundException());
        if(encoder.matches(loginRequest.getPassword(), registration.getPassword()))
        {
            List<String> li = new ArrayList<>();
            response.setResponse(true);
            for(GrantedAuthority authority : registration.getAuthorities()){
                li.add(authority.getAuthority());
            }
            response.setAuthority(li);
        }
        else{
            response.setResponse(false);
        }
        return response;
    }

    @Override
    public String deleteRegistration(String username) throws UserNameNotFoundException {
        UserRegistration registration =  userRepository.findByUsername(username).orElseThrow(()-> new UserNameNotFoundException());
        userRepository.delete(registration);
        return "Deleted Successfully";
    }
}
