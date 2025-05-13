package com.example.carrental.service.security;

import com.example.carrental.dto.request.user.SignInRequest;
import com.example.carrental.dto.request.user.SignOutRequest;
import com.example.carrental.dto.request.user.UserRegisterRequest;
import com.example.carrental.dto.request.user.UserRequest;
import com.example.carrental.dto.response.user.SignInResponse;
import com.example.carrental.entity.Role;
import com.example.carrental.entity.User;
import com.example.carrental.exception.role.RoleNotFoundException;
import com.example.carrental.exception.user.UserAlreadyTakenException;
import com.example.carrental.mapper.UserMapper;
import com.example.carrental.repository.RoleRepository;
import com.example.carrental.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(UserRegisterRequest userRequest) {

        Role role = roleRepository.findRoleByName(userRequest.isAdmin() ? "ADMIN" : "USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(userRequest.isAdmin() ? "ADMIN" : "USER");
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(Set.of(role));
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());
        user.setAge(userRequest.getAge());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            throw new UserAlreadyTakenException("Email is already in use");
        }
        return null;
    }

//    public SignInResponse signIn(SignInRequest signInRequest) {
//        String username = signInRequest.getUsername();
//        String password = signInRequest.getPassword();
//
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        return UserMapper.fromUserDetailsImpl(userDetails);
//    }

    public ResponseEntity<String> signIn(SignInRequest signInRequest, HttpServletRequest request) {
        System.out.println("🔍 Se încearcă autentificarea utilizatorului: " + signInRequest.getUsername());

        User user = userRepository.findUserByName(signInRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Setează sesiunea utilizatorului
        request.getSession().setAttribute("user", user);

        return ResponseEntity.ok("SUCCESS"); // ✅ Trimite un mesaj clar
    }





}
