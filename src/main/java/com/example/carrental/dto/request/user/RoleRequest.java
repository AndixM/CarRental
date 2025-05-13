package com.example.carrental.dto.request.user;

import com.example.carrental.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    private Integer roleId;

    @NotBlank
    private String name;

    @NotNull
    private List<User> users;

}
