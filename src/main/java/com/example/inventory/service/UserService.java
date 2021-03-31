package com.example.inventory.service;

import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.service.UserRegistrationServiceModel;

import java.util.List;

public interface UserService {
    boolean usernameExists(String username);

    void registerAndLogin(UserRegistrationServiceModel userRegistrationServiceModel);

    UserRegistrationServiceModel findByUsername(String name);

    void saveProfile(UserRegistrationServiceModel userRegistrationServiceModel);

    void edit(UserRegistrationServiceModel userRegistrationServiceModel, String role);
}
