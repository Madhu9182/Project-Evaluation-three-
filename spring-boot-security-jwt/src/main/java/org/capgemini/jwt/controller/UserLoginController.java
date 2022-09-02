package org.capgemini.jwt.controller;

import org.capgemini.jwt.model.LoginResponse;
import org.capgemini.jwt.model.UserLogin;
import org.capgemini.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/ap")
public class UserLoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JWTUtility jwtUtility;

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLogin user) throws Exception {
        LoginResponse loginResponse = restTemplate.postForObject("http://SignUp-Form:5009/api/login", user, LoginResponse.class);
        if(loginResponse.isResponse())
        {
        return jwtUtility.generateToken(user.getUsername(), loginResponse.getAuthority());
        }else
        {
            throw new Exception("Bad Credentials");
        }
    }

    @GetMapping("/check")
    public String getString(){
        return "Authentication is working";
    }
}
