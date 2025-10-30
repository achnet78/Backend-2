package com.example.Portfolio.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLogin {
    @Email(message = "email not valid")
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    @Size(min = 8, message = "password must be greater than 8 characters")
    private String password;
}