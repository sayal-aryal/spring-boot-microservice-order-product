package com.miu.authservice.service;

import com.miu.authservice.config.CustomUserDetails;
import com.miu.authservice.model.UserCredential;
import com.miu.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByName(username);
        var user = credential.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not found with Username: "+username));
        user.setRole(credential.get().getRole());
        return user;
    }
}
