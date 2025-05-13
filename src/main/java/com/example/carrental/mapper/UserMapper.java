package com.example.carrental.mapper;

import com.example.carrental.dto.request.user.UserRegisterRequest;
import com.example.carrental.dto.request.user.UserRequest;
import com.example.carrental.dto.response.user.SignInResponse;
import com.example.carrental.dto.response.user.UserResponse;
import com.example.carrental.entity.Role;
import com.example.carrental.entity.User;
import com.example.carrental.service.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public static UserResponse map(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .address(user.getAddress())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }

    public static User fromUserRequest(UserRegisterRequest userRequest, Optional<Role> role) {
        Set<Role> roleList = new HashSet<>();
        role.ifPresent(roleList::add);

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setAge(userRequest.getAge());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRoles(roleList);
        user.setPassword(userRequest.getPassword());
        return user;

    }

    public static User fromUserRequest(UserRequest userRequest) {
        User user = new User();
        user.setUserId(userRequest.getUserId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setAge(userRequest.getAge());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return user;

    }

    public static SignInResponse fromUserDetailsImpl(UserDetailsImpl userDetails) {
        SignInResponse response = new SignInResponse();

        response.setName(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        response.setAge(userDetails.getAge());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        response.setRole(roles);

        return response;
    }
}
