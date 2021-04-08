package com.example.inventory;

import com.example.inventory.model.entity.VatEntity;
import com.example.inventory.model.service.VatServiceModel;
import com.example.inventory.repository.VatRepository;
import com.example.inventory.service.VatService;
import com.example.inventory.service.impl.VatServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class InventoryApplicationTests {

    @Test
    void contextLoads() {
    }

}
