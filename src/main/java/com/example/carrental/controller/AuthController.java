package com.example.carrental.controller;

import com.example.carrental.dto.request.user.SignInRequest;
import com.example.carrental.dto.request.user.SignOutRequest;
import com.example.carrental.dto.request.user.UserRegisterRequest;
import com.example.carrental.dto.request.user.UserRequest;
import com.example.carrental.dto.response.user.SignInResponse;
import com.example.carrental.dto.response.user.SignOutResponse;
import com.example.carrental.service.security.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/register")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        System.out.println("Request primit la /api/register: " + userRegisterRequest);
        authService.registerUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/api/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInRequest signInRequest, HttpServletRequest request) {
        System.out.println("🔍 Request primit la /api/signin: " + signInRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute("user", authentication.getPrincipal());
        System.out.println("✅ Autentificare reușită pentru: " + signInRequest.getUsername());
        return ResponseEntity.ok("SUCCESS");
    }


    @PostMapping("/api/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok().body("User signed out successfully.");
    }


}
