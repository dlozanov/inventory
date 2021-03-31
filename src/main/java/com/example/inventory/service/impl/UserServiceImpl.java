package com.example.inventory.service.impl;

import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.exception.UserRoleNotFoundException;
import com.example.inventory.exception.WarehouseNotFoundException;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.UserRoleEntity;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRegistrationServiceModel;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.UserRoleRepository;
import com.example.inventory.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final InventoryUserService inventoryUserService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, InventoryUserService inventoryUserService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.inventoryUserService = inventoryUserService;
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void registerAndLogin(UserRegistrationServiceModel userRegistrationServiceModel) {
        UserEntity userEntity = modelMapper.map(userRegistrationServiceModel, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()));

        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRole.USER).orElseThrow(
                () -> new UserRoleNotFoundException("Потребителската роля не може да бъде открита."));

        userEntity.addRole(userRoleEntity);

        userEntity = userRepository.save(userEntity);

        UserDetails principal = inventoryUserService.loadUserByUsername(userEntity.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Override
    public UserRegistrationServiceModel findByUsername(String username) {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(userEntity, UserRegistrationServiceModel.class);
    }

    @Override
    public void saveProfile(UserRegistrationServiceModel userRegistrationServiceModel) {
        UserEntity userEntity = modelMapper.map(userRegistrationServiceModel, UserEntity.class);
        UserEntity oldUser = userRepository
                .findByUsername(userRegistrationServiceModel.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открит."));
        userEntity.setId(oldUser.getId());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()));
        userEntity.setRoles(oldUser.getRoles());
        userEntity.setFirm(oldUser.getFirm());
        userRepository.save(userEntity);
    }

    @Override
    public void edit(UserRegistrationServiceModel userRegistrationServiceModel, String role) {
        UserEntity userEntity = modelMapper.map(userRegistrationServiceModel, UserEntity.class);
        UserEntity oldUser = userRepository
                .findByUsername(userRegistrationServiceModel.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открит."));
        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRole.valueOf(role))
                .orElseThrow(() -> new UserRoleNotFoundException("Потребителската роля не може да бъде открита."));
        userEntity.setId(oldUser.getId());
        userEntity.setPassword(oldUser.getPassword());
        userEntity.setRoles(List.of(userRoleEntity));
        userEntity.setFirm(oldUser.getFirm());
        userRepository.save(userEntity);
    }
}
