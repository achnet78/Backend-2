package com.example.Portfolio.security;

import com.example.Portfolio.common_helpers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints related to user authentication and profile management")
public class UserController {

    private final UserService userService;


    @PostMapping("/register/admin")
    @Operation(summary = "Register a new admin user", description = "Allows an admin to register another admin user")
    public ResponseEntity<Map<String, String>> registerAdmin(
            @Valid @RequestBody UserRequest userRequest
    ) {
        return userService.registerAdmin(userRequest);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Revokes the current JWT token")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // The actual logout logic is handled by CustomLogoutHandler
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token")
    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody UserLogin userLogin
    ) {
        return userService.login(userLogin);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change user password", description = "Allows logged-in users to change their password")
    public ResponseEntity<ApiResponse> changePass(
            @Valid @RequestBody ChangePasswordRequest change,
            Authentication connectedUser
    ) {
        return userService.changePass(change, connectedUser);
    }

}