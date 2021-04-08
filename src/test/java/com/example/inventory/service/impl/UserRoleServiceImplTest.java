package com.example.inventory.service.impl;

import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.UserRoleEntity;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRoleServiceModel;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {

    private UserRoleEntity userRoleEntity1, userRoleEntity2;
    private UserEntity userEntity;

    @Mock
    UserRoleRepository mockUserRoleRepository;
    @Mock
    UserRepository mockUserRepository;

    UserRoleServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        userRoleEntity1 = new UserRoleEntity();
        userRoleEntity1.setRole(UserRole.USER);
        userRoleEntity2 = new UserRoleEntity();
        userRoleEntity2.setRole(UserRole.ADMIN);
        userEntity = new UserEntity();
        userEntity.setUsername("pesho");
        userEntity.setPassword("123456");
        userEntity.setEmail("pesho@pesho.bg");
        userEntity.setFullname("pesho peshov");
        userEntity.setPhone("123123123");
        userEntity.setRoles(List.of(userRoleEntity1, userRoleEntity2));

        serviceToTest = new UserRoleServiceImpl(mockUserRoleRepository, mockUserRepository, new ModelMapper());
    }

    @Test
    public void testUserRoleByUsername(){
        Mockito.when(mockUserRepository.findByUsername("pesho")).thenReturn(java.util.Optional.ofNullable(userEntity));

        List<UserRoleServiceModel> models = serviceToTest.findRolesByUsername("pesho");
        UserRoleServiceModel model1 = models.get(0);
        UserRoleServiceModel model2 = models.get(1);

        Assertions.assertEquals(models.size(), 2);
        Assertions.assertEquals(model1.getRole(), userRoleEntity1.getRole());
        Assertions.assertEquals(model2.getRole(), userRoleEntity2.getRole());
    }

}
