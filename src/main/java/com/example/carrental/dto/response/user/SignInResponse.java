package com.example.carrental.dto.response.user;

import com.example.carrental.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String name;
    private String email;
    private Integer age;
    private List<String> role;

}
