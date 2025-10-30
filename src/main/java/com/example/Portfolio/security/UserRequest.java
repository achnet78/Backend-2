package com.example.Portfolio.security;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserRequest {
    @NotBlank(message = "name required")
    @NotEmpty(message = "name required")
    private String name;
    @Email(message = "email not valid")
    @NotBlank(message = "email required")
    @NotEmpty(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    @NotEmpty(message = "password required")
    @Size(min = 8 , message = "password must be greater than 8 characters")
    private String password;
}