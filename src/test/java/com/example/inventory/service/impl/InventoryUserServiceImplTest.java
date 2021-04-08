package com.example.inventory.service.impl;

import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.UserRoleEntity;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class InventoryUserServiceImplTest {

    private InventoryUserService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void init(){
        serviceToTest = new InventoryUserService(mockUserRepository);
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                UserNotFoundException.class, () -> {
                    serviceToTest.loadUserByUsername("user_does_not_exits");
                }
        );
    }

    @Test
    void testExistingUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("ddddd");
        userEntity.setPassword("asdasd");

        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        UserRoleEntity roleAdmin = new UserRoleEntity();
        roleAdmin.setRole(UserRole.ADMIN);

        userEntity.setRoles(List.of(roleUser, roleAdmin));

        // configure mocks
        Mockito.when(mockUserRepository.findByUsername("ddddd")).
                thenReturn(Optional.of(userEntity));

        // test
        UserDetails userDetails = serviceToTest.loadUserByUsername("ddddd");

        Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }

}
