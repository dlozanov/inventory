package com.example.inventory.service;

import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    void seedUserRoles();
    List<UserRoleServiceModel> findRolesByUsername(String username);

    void getAccess(String name, List<UserRole> userRole);
}
