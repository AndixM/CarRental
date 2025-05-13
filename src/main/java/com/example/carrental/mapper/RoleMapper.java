package com.example.carrental.mapper;

import com.example.carrental.dto.request.user.RoleRequest;
import com.example.carrental.dto.response.user.RoleResponse;
import com.example.carrental.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RoleMapper {
    public static RoleResponse map(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
    public static Role map(RoleRequest role) {
        return Role.builder()
                .id(role.getRoleId())
                .name(role.getName())
                .build();
    }
}
