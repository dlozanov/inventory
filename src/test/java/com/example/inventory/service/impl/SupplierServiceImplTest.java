package com.example.inventory.service.impl;

import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.SupplierNotFoundException;
import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.SupplierEntity;
import com.example.inventory.model.service.ItemServiceModel;
import com.example.inventory.model.service.SupplierServiceModel;
import com.example.inventory.repository.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {

    private SupplierEntity supplierEntity;
    private FirmEntity firm;

    @Mock
    SupplierRepository mockRepositry;

    SupplierServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        firm = new FirmEntity();
        firm.setName("Firm1");
        firm.setBulstat("1231231231");
        firm.setTown("Sofia");
        firm.setAddress("Drujba 2");
        firm.setOwnerName("Denis");
        firm.setPhone("0883511440");
        firm.setEmail("firm1@gmail.com");

        supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setName("Supplier 1");
        supplierEntity.setEmail("supplier@supp.bg");
        supplierEntity.setPhone("0886546545");
        supplierEntity.setFirm(firm);

        serviceToTest = new SupplierServiceImpl(mockRepositry, null, new ModelMapper());
    }

    @Test
    void testSupplierNotFound(){
        Assertions.assertThrows(
                SupplierNotFoundException.class, () -> {
                    serviceToTest.findById(15);
                }
        );
    }

    @Test
    void testItemServiceFindById() {

        Mockito.when(mockRepositry.findById(1L)).thenReturn(java.util.Optional.ofNullable(supplierEntity));
        SupplierServiceModel model = serviceToTest.findById(1);

        Assertions.assertEquals(model.getId(), supplierEntity.getId());
        Assertions.assertEquals(model.getName(), supplierEntity.getName());
        Assertions.assertEquals(model.getEmail(), supplierEntity.getEmail());
        Assertions.assertEquals(model.getPhone(), supplierEntity.getPhone());
    }

}
