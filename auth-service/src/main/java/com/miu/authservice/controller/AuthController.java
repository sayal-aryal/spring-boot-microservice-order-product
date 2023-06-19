package com.miu.authservice.controller;

import com.miu.authservice.dto.AuthRequest;
import com.miu.authservice.model.UserCredential;
import com.miu.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authenticate.isAuthenticated()) {
                return service.generateToken(authRequest.getUsername(),authenticate.getAuthorities().stream().findFirst().get().toString());
            } else {
                return "Invalid Access";
//                throw new RuntimeException("Invalid Access");
            }
        } catch (Exception e) {
            return "Invalid Access";
//            throw new RuntimeException("Invalid Access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        try {
            service.validateToken(token);
            return "Token is Valid";
        } catch (Exception e) {
            return "Token is Invalid";
        }


    }
    //All methods require Spring Security control. But, since these methods need to be bypassed because at first we don't have user.
    //So, we use @EnableWebSecurity in AuthConfig

}
