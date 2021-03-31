package com.example.inventory.model.entity;

import com.example.inventory.model.entity.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity{

    private UserRole role;

    public UserRoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
