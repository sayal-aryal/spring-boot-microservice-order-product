package com.miu.authservice.service;

import com.miu.authservice.model.UserCredential;
import com.miu.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//
//
@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;//We have made a bean of PasswordEncoder in AuthConfig

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        repository.save(credential);
        return "User added to the System";
    }

    public String generateToken(String username,String role){
       return jwtService.generateToken(username,role);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
