package com.example.carrental.dto.response.user;

import com.example.carrental.entity.Reservation;
import com.example.carrental.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String address;
    private Integer age;
    private String phoneNumber;
    private List<String> roles;
    private Reservation reservation;
}
