package com.example.inventory.web;

import com.example.inventory.exception.UserRoleNotFoundException;
import com.example.inventory.model.entity.*;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.repository.*;
import com.example.inventory.service.StockGroupService;
import com.example.inventory.service.UserRoleService;
import com.example.inventory.service.UserService;
import com.example.inventory.service.VatService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FirmTestData {

    private UserRoleService userRoleService;
    private VatService vatService;
    private StockGroupService stockGroupService;
    private FirmRepository firmRepository;
    private ItemRepository itemRepository;
    private SupplierRepository supplierRepository;
    private TransactionRepository transactionRepository;
    private WarehouseRepository warehouseRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private StockGroupRepository stockGroupRepository;
    private VatRepository vatRepository;

    public FirmTestData(UserRoleService userRoleService, VatService vatService, StockGroupService stockGroupService, FirmRepository firmRepository, ItemRepository itemRepository, SupplierRepository supplierRepository, TransactionRepository transactionRepository, WarehouseRepository warehouseRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, StockGroupRepository stockGroupRepository, VatRepository vatRepository) {
        this.userRoleService = userRoleService;
        this.vatService = vatService;
        this.stockGroupService = stockGroupService;
        this.firmRepository = firmRepository;
        this.itemRepository = itemRepository;
        this.supplierRepository = supplierRepository;
        this.transactionRepository = transactionRepository;
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.stockGroupRepository = stockGroupRepository;
        this.vatRepository = vatRepository;
    }

    public void init(){

        userRoleService.seedUserRoles();
        vatService.seedVats();
        stockGroupService.seedStockGroups();

        FirmEntity firmEntity = new FirmEntity();
        firmEntity.setName("Firm1");
        firmEntity.setBulstat("1231231231");
        firmEntity.setTown("Sofia");
        firmEntity.setAddress("Drujba 2");
        firmEntity.setOwnerName("denis");
        firmEntity.setPhone("0883511440");
        firmEntity.setEmail("firm1@gmail.com");

        WarehouseEntity warehouse1 = new WarehouseEntity();
        warehouse1.setAddress("Drujba 2");
        warehouse1.setTown("Sofia");
        warehouse1 = warehouseRepository.save(warehouse1);
        WarehouseEntity warehouse2 = new WarehouseEntity();
        warehouse2.setAddress("Iztok");
        warehouse2.setTown("Vraca");
        warehouse2 = warehouseRepository.save(warehouse2);
        firmEntity.setWarehouses(List.of(warehouse1, warehouse2));

        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(1);
        supplier.setName("Supplier 1");
        supplier.setEmail("supplier@supp.bg");
        supplier.setPhone("0886546545");
        supplier = supplierRepository.save(supplier);
        firmEntity.setSuppliers(List.of(supplier));

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("denis");
        userEntity.setPassword("123123");
        userEntity.setFullname("Denis");
        userEntity.setEmail("denis@abv.bg");
        userEntity.setPhone("0883511440");
        UserRoleEntity userRoleEntity1 = userRoleRepository.findByRole(UserRole.USER).orElseThrow(
                () -> new UserRoleNotFoundException("Потребителската роля не може да бъде открита."));
        UserRoleEntity userRoleEntity2 = userRoleRepository.findByRole(UserRole.ADMIN).orElseThrow(
                () -> new UserRoleNotFoundException("Потребителската роля не може да бъде открита."));
        userEntity.setRoles(List.of(userRoleEntity1, userRoleEntity2));
        userEntity = userRepository.save(userEntity);
        firmEntity.setUsers(List.of(userEntity));

        List<VatEntity> vats = vatRepository.findAll();
        ItemEntity item1 = new ItemEntity();
        item1.setName("Item 1");
        item1.setBarcode("1212121212");
        item1.setIncomingPrice(BigDecimal.valueOf(1.25));
        item1.setOutgoingPrice(BigDecimal.valueOf(2.25));
        item1.setSupplier(supplier);
        item1.setQuantity(155);
        item1.setDescription("Item 1 description field");
        item1.setGroup(stockGroupRepository.findById(1L).orElseThrow());
        item1.setVat(vats.get(0));
        item1.setWarehouse(warehouse1);
        item1 = itemRepository.save(item1);
        ItemEntity item2 = new ItemEntity();
        item2.setName("Item 2");
        item2.setBarcode("22222222");
        item2.setIncomingPrice(BigDecimal.valueOf(4.25));
        item2.setOutgoingPrice(BigDecimal.valueOf(15.25));
        item2.setSupplier(supplier);
        item2.setQuantity(256);
        item2.setDescription("Item 2 description field");
        item2.setGroup(stockGroupRepository.findById(2L).orElseThrow());
        item2.setVat(vats.get(1));
        item2.setWarehouse(warehouse2);
        item2 = itemRepository.save(item2);
        firmEntity.setItems(List.of(item1, item2));

        TransactionEntity transaction1 = new TransactionEntity();
        transaction1.setTransactionType(TransactionType.SELL);
        transaction1.setTransactionStatus(TransactionStatus.APPROVED);
        transaction1.setDateTime(LocalDateTime.now());
        transaction1.setPrice(BigDecimal.valueOf(12.25));
        transaction1.setQuantity(100);
        transaction1.setSum(transaction1.getPrice().multiply(BigDecimal.valueOf(100)));
        transaction1 = transactionRepository.save(transaction1);
        TransactionEntity transaction2 = new TransactionEntity();
        transaction2.setTransactionType(TransactionType.PURCHASE);
        transaction2.setTransactionStatus(TransactionStatus.APPROVED);
        transaction2.setDateTime(LocalDateTime.now());
        transaction2.setPrice(BigDecimal.valueOf(1.25));
        transaction2.setQuantity(10);
        transaction2.setSum(transaction2.getPrice().multiply(BigDecimal.valueOf(10)));
        transaction2 = transactionRepository.save(transaction2);
        TransactionEntity transaction3 = new TransactionEntity();
        transaction3.setTransactionType(TransactionType.SELL);
        transaction3.setTransactionStatus(TransactionStatus.PENDING);
        transaction3.setDateTime(LocalDateTime.now());
        transaction3.setPrice(BigDecimal.valueOf(155.25));
        transaction3.setQuantity(5252);
        transaction3.setSum(transaction3.getPrice().multiply(BigDecimal.valueOf(5252)));
        transaction3 = transactionRepository.save(transaction3);
        firmEntity.setTransactions(List.of(transaction1, transaction2, transaction3));

        firmRepository.save(firmEntity);

        userEntity.setFirm(firmEntity);
        userRepository.save(userEntity);

        item1.setFirm(firmEntity);
        itemRepository.save(item1);
        item2.setFirm(firmEntity);
        itemRepository.save(item2);
    }

    public void cleanUp(){
        firmRepository.findAll().stream().forEach(firmEntity -> {
            firmEntity.setTransactions(null);
            firmEntity.setItems(null);
            firmEntity.setUsers(null);
            firmEntity.setSuppliers(null);
            firmEntity.setWarehouses(null);
            firmRepository.save(firmEntity);
            firmRepository.flush();
        });
        itemRepository.findAll().stream().forEach(itemEntity -> {
            itemEntity.setGroup(null);
            itemEntity.setVat(null);
            itemEntity.setSupplier(null);
            itemEntity.setFirm(null);
            itemRepository.save(itemEntity);
            itemRepository.flush();
        });
        supplierRepository.findAll().stream().forEach(supplierEntity -> {
            supplierEntity.setFirm(null);
            supplierRepository.save(supplierEntity);
            supplierRepository.flush();
        });
        warehouseRepository.findAll().stream().forEach(warehouseEntity -> {
            warehouseEntity.setFirm(null);
            warehouseRepository.save(warehouseEntity);
            warehouseRepository.flush();
        });
        transactionRepository.findAll().stream().forEach(transactionEntity -> {
            transactionEntity.setFirm(null);
            transactionEntity.setBuyer(null);
            transactionEntity.setItem(null);
            transactionEntity.setUser(null);
            transactionRepository.save(transactionEntity);
            transactionRepository.flush();
        });
        itemRepository.deleteAll();
        userRepository.deleteAll();
        firmRepository.deleteAll();
        transactionRepository.deleteAll();
        supplierRepository.deleteAll();
        warehouseRepository.deleteAll();
    }
}
