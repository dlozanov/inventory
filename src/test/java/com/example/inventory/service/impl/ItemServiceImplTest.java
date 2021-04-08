package com.example.inventory.service.impl;

import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.model.entity.*;
import com.example.inventory.model.service.ItemServiceModel;
import com.example.inventory.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    private ItemEntity item1, item2;
    private StockGroupEntity group;
    private VatEntity vat;
    private FirmEntity firm;
    private WarehouseEntity warehouse;
    private SupplierEntity supplier;

    @Mock
    ItemRepository mockItemRepository;

    ItemServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        group = new StockGroupEntity();
        group.setName("Group 1");
        vat = new VatEntity();
        vat.setLetter('A');
        vat.setPercent(20);
        firm = new FirmEntity();
        firm.setName("Firm1");
        firm.setBulstat("1231231231");
        firm.setTown("Sofia");
        firm.setAddress("Drujba 2");
        firm.setOwnerName("Denis");
        firm.setPhone("0883511440");
        firm.setEmail("firm1@gmail.com");
        warehouse = new WarehouseEntity();
        warehouse.setId(5);
        warehouse.setTown("Sofia");
        warehouse.setAddress("ul. Nezabravka");
        warehouse.setFirm(firm);
        supplier = new SupplierEntity();
        supplier.setId(1);
        supplier.setEmail("supplier@sup.bg");
        supplier.setName("Supplier 1");

        item1 = new ItemEntity();
        item1.setName("Item 1");
        item1.setBarcode("1212121212");
        item1.setGroup(group);
        item1.setVat(vat);
        item1.setIncomingPrice(BigDecimal.valueOf(1.25));
        item1.setOutgoingPrice(BigDecimal.valueOf(2.25));
        item1.setFirm(firm);
        item1.setWarehouse(warehouse);
        item1.setSupplier(supplier);
        item1.setQuantity(155);
        item1.setDescription("Item 1 description field");

        item2 = new ItemEntity();
        item2.setName("Item 2");
        item2.setBarcode("22222222");
        item2.setGroup(group);
        item2.setVat(vat);
        item2.setIncomingPrice(BigDecimal.valueOf(4.25));
        item2.setOutgoingPrice(BigDecimal.valueOf(15.25));
        item2.setFirm(firm);
        item2.setWarehouse(warehouse);
        item2.setSupplier(supplier);
        item2.setQuantity(256);
        item2.setDescription("Item 2 description field");

        serviceToTest = new ItemServiceImpl(mockItemRepository, null, null,
                null, null, null, null, null);

    }

    @Test
    void testItemNotFound(){
        Assertions.assertThrows(
                ItemNotFoundException.class, () -> {
                    serviceToTest.findById(15);
                }
        );
    }

    @Test
    void testItemServiceFindById() {

        Mockito.when(mockItemRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(item1));
        ItemServiceModel model1 = serviceToTest.findById(1);

        Assertions.assertEquals(model1.getName(), item1.getName());
        Assertions.assertEquals(model1.getBarcode(), item1.getBarcode());
        Assertions.assertEquals(model1.getGroup(), item1.getGroup().getName());
        Assertions.assertEquals(model1.getVat(), item1.getVat().getLetter());
        Assertions.assertEquals(model1.getIncomingPrice(), item1.getIncomingPrice());
        Assertions.assertEquals(model1.getOutgoingPrice(), item1.getOutgoingPrice());
        Assertions.assertEquals(model1.getWarehouse(), item1.getWarehouse().getId());
        Assertions.assertEquals(model1.getSupplier(), item1.getSupplier().getId());
        Assertions.assertEquals(model1.getQuantity(), item1.getQuantity());
        Assertions.assertEquals(model1.getDescription(), item1.getDescription());

        Mockito.when(mockItemRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(item2));
        ItemServiceModel model2 = serviceToTest.findById(2);

        Assertions.assertEquals(model2.getName(), item2.getName());
        Assertions.assertEquals(model2.getBarcode(), item2.getBarcode());
        Assertions.assertEquals(model2.getGroup(), item2.getGroup().getName());
        Assertions.assertEquals(model2.getVat(), item2.getVat().getLetter());
        Assertions.assertEquals(model2.getIncomingPrice(), item2.getIncomingPrice());
        Assertions.assertEquals(model2.getOutgoingPrice(), item2.getOutgoingPrice());
        Assertions.assertEquals(model2.getWarehouse(), item2.getWarehouse().getId());
        Assertions.assertEquals(model2.getSupplier(), item2.getSupplier().getId());
        Assertions.assertEquals(model2.getQuantity(), item2.getQuantity());
        Assertions.assertEquals(model2.getDescription(), item2.getDescription());
    }

    @Test
    public void testItemServiceHasQuantity(){
        Mockito.when(mockItemRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(item1));
        ItemServiceModel model1 = serviceToTest.findById(1);

        boolean hasQuantity = serviceToTest.hasEnoughQuantity(1, 120);
        Assertions.assertEquals(hasQuantity, true);

        hasQuantity = serviceToTest.hasEnoughQuantity(1, 5555);
        Assertions.assertEquals(hasQuantity, false);
    }
}
