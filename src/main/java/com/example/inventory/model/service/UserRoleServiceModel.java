package com.example.inventory.model.service;

import com.example.inventory.model.entity.enums.UserRole;

public class UserRoleServiceModel {

    private UserRole role;

    public UserRoleServiceModel() {
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
