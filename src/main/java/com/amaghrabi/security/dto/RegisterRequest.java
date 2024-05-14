package com.amaghrabi.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
