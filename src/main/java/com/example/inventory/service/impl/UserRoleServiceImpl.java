package com.example.inventory.service.impl;

import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.exception.UserRoleNoPermissionsException;
import com.example.inventory.exception.WarehouseNotFoundException;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.UserRoleEntity;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRoleServiceModel;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.UserRoleRepository;
import com.example.inventory.service.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUserRoles() {
        if(userRoleRepository.count() == 0){
            UserRoleEntity adminRole = new UserRoleEntity();
            UserRoleEntity moderatorRole = new UserRoleEntity();
            UserRoleEntity userRole = new UserRoleEntity();

            adminRole.setRole(UserRole.ADMIN);
            moderatorRole.setRole(UserRole.MODERATOR);
            userRole.setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, moderatorRole, userRole));
        }
    }

    @Override
    public List<UserRoleServiceModel> findRolesByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открит."));
        return userEntity.getRoles().stream().map(ur -> modelMapper.map(ur, UserRoleServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void getAccess(String name, List<UserRole> userRole) {
        List<UserRoleServiceModel> rolesByUsername = findRolesByUsername(name);
        boolean hasAccess = false;
        for (UserRoleServiceModel u : rolesByUsername) {
            for(UserRole ur : userRole){
                if(u.getRole() == ur){
                    hasAccess = true;
                    break;
                }
            }
        }
        if(!hasAccess)
            throw new UserRoleNoPermissionsException("Нямате достъп до това меню.");
    }
}
