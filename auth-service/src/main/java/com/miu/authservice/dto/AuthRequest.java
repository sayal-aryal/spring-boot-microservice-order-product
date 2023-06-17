package com.miu.authservice.dto;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String username;
    private String password;
}
