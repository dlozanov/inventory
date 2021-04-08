package com.example.inventory.service.impl;

import com.example.inventory.exception.SupplierNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.UserRoleEntity;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRegistrationServiceModel;
import com.example.inventory.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserEntity userEntity;
    private UserRoleEntity userRoleEntity1, userRoleEntity2;

    @Mock
    UserRepository mockUserRepository;

    UserServiceImpl serviceToTest;

    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private List<UserRoleEntity> roles = new ArrayList<>();
    private FirmEntity firm;
    @BeforeEach
    public void init(){
        userRoleEntity1 = new UserRoleEntity();
        userRoleEntity1.setRole(UserRole.USER);
        userRoleEntity2 = new UserRoleEntity();
        userRoleEntity2.setRole(UserRole.ADMIN);
        userEntity = new UserEntity();
        userEntity.setUsername("denis");
        userEntity.setPassword("123123");
        userEntity.setFullname("Denis");
        userEntity.setEmail("denis@abv.bg");
        userEntity.setPhone("0883511440");
        userEntity.setRoles(List.of(userRoleEntity1, userRoleEntity2));

        serviceToTest = new UserServiceImpl(mockUserRepository, new ModelMapper(), null, null, null);
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    serviceToTest.findByUsername("dsa");
                }
        );
    }

    @Test
    public void testUserServiceUserExists(){
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));

        boolean exists = serviceToTest.usernameExists("denis");
        Assertions.assertEquals(exists, true);
    }

    @Test
    public void testUserServiceFindByUsername(){
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));

        UserRegistrationServiceModel model = serviceToTest.findByUsername("denis");
        Assertions.assertEquals(model.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(model.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(model.getFullname(), userEntity.getFullname());
        Assertions.assertEquals(model.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(model.getPhone(), userEntity.getPhone());
    }

}
