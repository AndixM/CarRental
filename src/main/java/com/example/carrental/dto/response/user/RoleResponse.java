package com.example.carrental.dto.response.user;

import com.example.carrental.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Integer id;
    private String name;
    private List<User> users;
}
