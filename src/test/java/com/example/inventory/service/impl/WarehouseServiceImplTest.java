package com.example.inventory.service.impl;

import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.WarehouseEntity;
import com.example.inventory.model.service.WarehouseServiceModel;
import com.example.inventory.repository.FirmRepository;
import com.example.inventory.repository.WarehouseRepository;
import com.example.inventory.service.FirmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplTest {

    private WarehouseEntity warehouse1, warehouse2;
    private FirmEntity firm1, firm2;

    @Mock
    WarehouseRepository mockWarehouseRepsoitory;
    @Mock
    FirmRepository mockFirmRepository;
    @Mock
    FirmService firmService;

    private WarehouseServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        warehouse1 = new WarehouseEntity();
        warehouse1.setAddress("Drujba 2");
        warehouse1.setTown("Sofia");

        firm1 = new FirmEntity();
        firm1.setName("Firm1");
        firm1.setBulstat("1231231231");
        firm1.setTown("Sofia");
        firm1.setAddress("Drujba 2");
        firm1.setOwnerName("Denis");
        firm1.setPhone("0883511440");
        firm1.setEmail("firm1@gmail.com");
        warehouse1.setFirm(firm1);

        warehouse2 = new WarehouseEntity();
        warehouse2.setAddress("Iztok");
        warehouse2.setTown("Vraca");

        firm2 = new FirmEntity();
        firm2.setName("VracaFirm");
        firm2.setBulstat("1111111111");
        firm2.setTown("Vraca");
        firm2.setAddress("Centur");
        firm2.setOwnerName("Somebody");
        firm2.setPhone("0888523654");
        firm2.setEmail("firm2@gmail.com");
        warehouse2.setFirm(firm2);

        serviceToTest = new WarehouseServiceImpl(mockWarehouseRepsoitory, mockFirmRepository, firmService, new ModelMapper());
    }

    @Test
    public void testWarehouseFindAll(){
        Mockito.when(mockWarehouseRepsoitory.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouse1));
        Mockito.when(mockWarehouseRepsoitory.findById(2L)).thenReturn(java.util.Optional.ofNullable(warehouse2));
        WarehouseServiceModel model1 = serviceToTest.findById(1);
        WarehouseServiceModel model2 = serviceToTest.findById(2);

        Assertions.assertEquals(warehouse1.getAddress(), model1.getAddress());
        Assertions.assertEquals(warehouse1.getTown(), model1.getTown());

        Assertions.assertEquals(warehouse2.getAddress(), model2.getAddress());
        Assertions.assertEquals(warehouse2.getTown(), model2.getTown());
    }
}
