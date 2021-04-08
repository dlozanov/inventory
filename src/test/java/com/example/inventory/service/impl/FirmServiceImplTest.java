package com.example.inventory.service.impl;

import com.example.inventory.model.entity.*;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.SupplierServiceModel;
import com.example.inventory.model.service.TransactionServiceModel;
import com.example.inventory.model.service.UserRegistrationServiceModel;
import com.example.inventory.model.service.WarehouseServiceModel;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.model.view.ItemViewModel;
import com.example.inventory.model.view.TransactionPendingViewModel;
import com.example.inventory.repository.FirmRepository;
import com.example.inventory.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FirmServiceImplTest {

    private FirmEntity firmEntity;
    private WarehouseEntity warehouse1, warehouse2;
    private SupplierEntity supplier;
    private UserEntity userEntity;
    private UserRoleEntity userRoleEntity1, userRoleEntity2;
    private ItemEntity item1, item2;
    private TransactionEntity transaction1, transaction2, transaction3;
    private StockGroupEntity group;
    private VatEntity vat;

    @Mock
    FirmRepository mockFirmRepository;
    @Mock
    UserRepository mockUserRepository;

    FirmServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        firmEntity = new FirmEntity();
        firmEntity.setName("Firm1");
        firmEntity.setBulstat("1231231231");
        firmEntity.setTown("Sofia");
        firmEntity.setAddress("Drujba 2");
        firmEntity.setOwnerName("Denis");
        firmEntity.setPhone("0883511440");
        firmEntity.setEmail("firm1@gmail.com");

        warehouse1 = new WarehouseEntity();
        warehouse1.setAddress("Drujba 2");
        warehouse1.setTown("Sofia");
        warehouse2 = new WarehouseEntity();
        warehouse2.setAddress("Iztok");
        warehouse2.setTown("Vraca");
        firmEntity.setWarehouses(List.of(warehouse1, warehouse2));

        supplier = new SupplierEntity();
        supplier.setId(1);
        supplier.setName("Supplier 1");
        supplier.setEmail("supplier@supp.bg");
        supplier.setPhone("0886546545");
        firmEntity.setSuppliers(List.of(supplier));

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
        firmEntity.setUsers(List.of(userEntity));

        group = new StockGroupEntity();
        group.setName("Group 1");
        vat = new VatEntity();
        vat.setLetter('A');
        vat.setPercent(20);
        item1 = new ItemEntity();
        item1.setName("Item 1");
        item1.setBarcode("1212121212");
        item1.setIncomingPrice(BigDecimal.valueOf(1.25));
        item1.setOutgoingPrice(BigDecimal.valueOf(2.25));
        item1.setSupplier(supplier);
        item1.setQuantity(155);
        item1.setDescription("Item 1 description field");
        item1.setGroup(group);
        item1.setVat(vat);
        item1.setWarehouse(warehouse1);
        item2 = new ItemEntity();
        item2.setName("Item 2");
        item2.setBarcode("22222222");
        item2.setIncomingPrice(BigDecimal.valueOf(4.25));
        item2.setOutgoingPrice(BigDecimal.valueOf(15.25));
        item2.setSupplier(supplier);
        item2.setQuantity(256);
        item2.setDescription("Item 2 description field");
        item2.setGroup(group);
        item2.setVat(vat);
        item2.setWarehouse(warehouse2);
        firmEntity.setItems(List.of(item1, item2));

        transaction1 = new TransactionEntity();
        transaction1.setTransactionType(TransactionType.SELL);
        transaction1.setTransactionStatus(TransactionStatus.APPROVED);
        transaction1.setDateTime(LocalDateTime.now());
        transaction1.setPrice(BigDecimal.valueOf(12.25));
        transaction1.setQuantity(100);
        transaction1.setSum(transaction1.getPrice().multiply(BigDecimal.valueOf(100)));
        transaction2 = new TransactionEntity();
        transaction2.setTransactionType(TransactionType.PURCHASE);
        transaction2.setTransactionStatus(TransactionStatus.APPROVED);
        transaction2.setDateTime(LocalDateTime.now());
        transaction2.setPrice(BigDecimal.valueOf(1.25));
        transaction2.setQuantity(10);
        transaction2.setSum(transaction2.getPrice().multiply(BigDecimal.valueOf(10)));
        transaction3 = new TransactionEntity();
        transaction3.setTransactionType(TransactionType.SELL);
        transaction3.setTransactionStatus(TransactionStatus.PENDING);
        transaction3.setDateTime(LocalDateTime.now());
        transaction3.setPrice(BigDecimal.valueOf(155.25));
        transaction3.setQuantity(5252);
        transaction3.setSum(transaction3.getPrice().multiply(BigDecimal.valueOf(5252)));
        firmEntity.setTransactions(List.of(transaction1, transaction2, transaction3));

        serviceToTest = new FirmServiceImpl(mockFirmRepository, null,
                new ModelMapper(), null, mockUserRepository, null);
    }

    @Test
    public void testFirmServiceFirmNameExists(){
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        boolean exists = serviceToTest.firmNameExists("Firm1");
        Assertions.assertEquals(exists, true);
        exists = serviceToTest.firmNameExists("Firm2");
        Assertions.assertEquals(exists, false);
    }

    @Test
    public void testFirmServiceFirmBulstatExists(){
        Mockito.when(mockFirmRepository.findByBulstat("1231231231")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        boolean exists = serviceToTest.firmBulstatExists("1231231231");
        Assertions.assertEquals(exists, true);
        exists = serviceToTest.firmNameExists("Firm2");
        Assertions.assertEquals(exists, false);
    }

    @Test
    public void testFirmServiceGetFirmByUsername(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));

        FirmViewModel firmFromUsername = serviceToTest.getFirmFromUsername("denis");
        Assertions.assertEquals(firmFromUsername.getAddress(), firmEntity.getAddress());
        Assertions.assertEquals(firmFromUsername.getBulstat(), firmEntity.getBulstat());
        Assertions.assertEquals(firmFromUsername.getEmail(), firmEntity.getEmail());
        Assertions.assertEquals(firmFromUsername.getName(), firmEntity.getName());
        Assertions.assertEquals(firmFromUsername.getOwnerName(), firmEntity.getOwnerName());
        Assertions.assertEquals(firmFromUsername.getPhone(), firmEntity.getPhone());
        Assertions.assertEquals(firmFromUsername.getTown(), firmEntity.getTown());
    }

    @Test
    public void testFirmServiceGetFirmByName(){
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        FirmViewModel model = serviceToTest.getFirmByName("Firm1");
        Assertions.assertEquals(model.getAddress(), firmEntity.getAddress());
        Assertions.assertEquals(model.getBulstat(), firmEntity.getBulstat());
        Assertions.assertEquals(model.getEmail(), firmEntity.getEmail());
        Assertions.assertEquals(model.getName(), firmEntity.getName());
        Assertions.assertEquals(model.getOwnerName(), firmEntity.getOwnerName());
        Assertions.assertEquals(model.getPhone(), firmEntity.getPhone());
        Assertions.assertEquals(model.getTown(), firmEntity.getTown());
    }

    @Test
    public void testFirmServiceFindAll(){
        Mockito.when(mockFirmRepository.findAll()).thenReturn(List.of(firmEntity));

        List<FirmViewModel> models = serviceToTest.getAllFirms();
        FirmViewModel model = models.get(0);
        Assertions.assertEquals(model.getAddress(), firmEntity.getAddress());
        Assertions.assertEquals(model.getBulstat(), firmEntity.getBulstat());
        Assertions.assertEquals(model.getEmail(), firmEntity.getEmail());
        Assertions.assertEquals(model.getName(), firmEntity.getName());
        Assertions.assertEquals(model.getOwnerName(), firmEntity.getOwnerName());
        Assertions.assertEquals(model.getPhone(), firmEntity.getPhone());
        Assertions.assertEquals(model.getTown(), firmEntity.getTown());
    }

    @Test
    public void testFirmServiceGetAllWarehouses(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<WarehouseServiceModel> models = serviceToTest.getAllWarhouses("denis");

        Assertions.assertEquals(2, models.size());

        WarehouseServiceModel model1 = models.get(0);
        WarehouseServiceModel model2 = models.get(1);

        Assertions.assertEquals(model1.getAddress(), warehouse1.getAddress());
        Assertions.assertEquals(model1.getTown(), warehouse1.getTown());

        Assertions.assertEquals(model2.getAddress(), warehouse2.getAddress());
        Assertions.assertEquals(model2.getTown(), warehouse2.getTown());
    }

    @Test
    public void testFirmServiceGetAllItems(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<ItemViewModel> models = serviceToTest.getAllItems("denis");

        Assertions.assertEquals(2, models.size());

        ItemViewModel model1 = models.get(0);
        ItemViewModel model2 = models.get(1);

        Assertions.assertEquals(model1.getBarcode(), item1.getBarcode());
        Assertions.assertEquals(model1.getDescription(), item1.getDescription());
        Assertions.assertEquals(model1.getName(), item1.getName());
        Assertions.assertEquals(model1.getIncomingPrice(), item1.getIncomingPrice());
        Assertions.assertEquals(model1.getOutgoingPrice(), item1.getOutgoingPrice());
        Assertions.assertEquals(model1.getSupplierName(), item1.getSupplier().getName());
        Assertions.assertEquals(model1.getQuantity(), item1.getQuantity());

        Assertions.assertEquals(model2.getBarcode(), item2.getBarcode());
        Assertions.assertEquals(model2.getDescription(), item2.getDescription());
        Assertions.assertEquals(model2.getName(), item2.getName());
        Assertions.assertEquals(model2.getIncomingPrice(), item2.getIncomingPrice());
        Assertions.assertEquals(model2.getOutgoingPrice(), item2.getOutgoingPrice());
        Assertions.assertEquals(model2.getSupplierName(), item2.getSupplier().getName());
        Assertions.assertEquals(model2.getQuantity(), item2.getQuantity());
    }

    @Test
    public void testFirmServiceGetAllSuppliers(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<SupplierServiceModel> models = serviceToTest.getAllSuppliers("denis");

        Assertions.assertEquals(1, models.size());

        SupplierServiceModel model1 = models.get(0);

        Assertions.assertEquals(model1.getName(), supplier.getName());
        Assertions.assertEquals(model1.getEmail(), supplier.getEmail());
        Assertions.assertEquals(model1.getPhone(), supplier.getPhone());
    }

    @Test
    public void testFirmServiceGetAllUsers(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<UserRegistrationServiceModel> models = serviceToTest.getAllUsers("denis");

        Assertions.assertEquals(1, models.size());

        UserRegistrationServiceModel model1 = models.get(0);

        Assertions.assertEquals(model1.getFullname(), userEntity.getFullname());
        Assertions.assertEquals(model1.getPhone(), userEntity.getPhone());
        Assertions.assertEquals(model1.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(model1.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(model1.getUsername(), userEntity.getUsername());
    }

    @Test
    public void testFirmServiceGetAllPendingTransactions(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<TransactionPendingViewModel> models = serviceToTest.getAllPendingTransactions("denis");

        Assertions.assertEquals(1, models.size());

        TransactionPendingViewModel model1 = models.get(0);

        Assertions.assertEquals(model1.getAddress(), transaction3.getAddress());
        Assertions.assertEquals(model1.getId(), transaction3.getId());
        Assertions.assertEquals(model1.getPrice(), transaction3.getPrice());
        Assertions.assertEquals(model1.getQuantity(), transaction3.getQuantity());
        Assertions.assertEquals(model1.getSum(), transaction3.getSum());
    }

    @Test
    public void testFirmServiceGetAllTransactions(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<TransactionServiceModel> models = serviceToTest.getAllTransactions("denis");

        Assertions.assertEquals(3, models.size());

        TransactionServiceModel model1 = models.get(0);
        TransactionServiceModel model2 = models.get(1);
        TransactionServiceModel model3 = models.get(2);

        Assertions.assertEquals(model1.getAddress(), transaction1.getAddress());
        Assertions.assertEquals(model1.getId(), transaction1.getId());
        Assertions.assertEquals(model1.getPrice(), transaction1.getPrice());
        Assertions.assertEquals(model1.getQuantity(), transaction1.getQuantity());
        Assertions.assertEquals(model1.getSum(), transaction1.getSum());
        Assertions.assertEquals(model1.getDateTime(), transaction1.getDateTime());
        Assertions.assertEquals(model1.getTransactionStatus(), transaction1.getTransactionStatus());
        Assertions.assertEquals(model1.getTransactionType(), transaction1.getTransactionType());

        Assertions.assertEquals(model2.getAddress(), transaction2.getAddress());
        Assertions.assertEquals(model2.getId(), transaction2.getId());
        Assertions.assertEquals(model2.getPrice(), transaction2.getPrice());
        Assertions.assertEquals(model2.getQuantity(), transaction2.getQuantity());
        Assertions.assertEquals(model2.getSum(), transaction2.getSum());
        Assertions.assertEquals(model2.getDateTime(), transaction2.getDateTime());
        Assertions.assertEquals(model2.getTransactionStatus(), transaction2.getTransactionStatus());
        Assertions.assertEquals(model2.getTransactionType(), transaction2.getTransactionType());

        Assertions.assertEquals(model3.getAddress(), transaction3.getAddress());
        Assertions.assertEquals(model3.getId(), transaction3.getId());
        Assertions.assertEquals(model3.getPrice(), transaction3.getPrice());
        Assertions.assertEquals(model3.getQuantity(), transaction3.getQuantity());
        Assertions.assertEquals(model3.getSum(), transaction3.getSum());
        Assertions.assertEquals(model3.getDateTime(), transaction3.getDateTime());
        Assertions.assertEquals(model3.getTransactionStatus(), transaction3.getTransactionStatus());
        Assertions.assertEquals(model3.getTransactionType(), transaction3.getTransactionType());
    }

    @Test
    public void testFirmServiceGetAllSellTransactions(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        List<TransactionServiceModel> models = serviceToTest.getAllSellTransactions("denis");

        Assertions.assertEquals(1, models.size());

        TransactionServiceModel model1 = models.get(0);

        Assertions.assertEquals(model1.getAddress(), transaction1.getAddress());
        Assertions.assertEquals(model1.getId(), transaction1.getId());
        Assertions.assertEquals(model1.getPrice(), transaction1.getPrice());
        Assertions.assertEquals(model1.getQuantity(), transaction1.getQuantity());
        Assertions.assertEquals(model1.getSum(), transaction1.getSum());
        Assertions.assertEquals(model1.getDateTime(), transaction1.getDateTime());
        Assertions.assertEquals(model1.getTransactionStatus(), transaction1.getTransactionStatus());
        Assertions.assertEquals(model1.getTransactionType(), transaction1.getTransactionType());
    }

    @Test
    public void testFirmServiceGetAllSellTransactionsSum(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        BigDecimal sum = serviceToTest.getAllTransactionsSum("denis");

        Assertions.assertEquals(sum, transaction1.getSum().subtract(transaction2.getSum()));
    }

    @Test
    public void testFirmServiceGetAllSellTransactionsQuantity(){
        userEntity.setFirm(firmEntity);
        Mockito.when(mockUserRepository.findByUsername("denis")).thenReturn(java.util.Optional.ofNullable(userEntity));
        Mockito.when(mockFirmRepository.findByName("Firm1")).thenReturn(java.util.Optional.ofNullable(firmEntity));

        double qty = serviceToTest.getAllTransactionsQuantity("denis");

        Assertions.assertEquals(qty, transaction1.getQuantity() - transaction2.getQuantity());
    }
}
