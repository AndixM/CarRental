package com.example.carrental.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignOutResponse {
    private String name;
    private String email;
    private Integer age;

}