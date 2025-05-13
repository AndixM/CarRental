package com.example.carrental.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Integer age;
    private String phoneNumber;
    private Integer reservationId;
    private String role;
}
