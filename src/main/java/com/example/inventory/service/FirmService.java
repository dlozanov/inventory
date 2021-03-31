package com.example.inventory.service;

import com.example.inventory.model.service.*;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.model.view.ItemViewModel;
import com.example.inventory.model.view.TransactionPendingViewModel;
import com.example.inventory.model.view.WarehouseViewModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface FirmService {
    boolean firmNameExists(String name);

    boolean firmBulstatExists(String bulstat);

    void save(FirmServiceModel firmServiceModel, String name) throws IOException;

    FirmViewModel getFirmFromUsername(String username);

    FirmViewModel getFirmByName(String name);

    void edit(FirmServiceModel firmServiceModel, String name) throws IOException;

    List<WarehouseServiceModel> getAllWarhouses(String name);

    List<ItemViewModel> getAllItems(String name);

    List<ItemViewModel> getAllItemsByFirmName(String name);

    List<SupplierServiceModel> getAllSuppliers(String name);

    List<UserRegistrationServiceModel> getAllUsers(String name);

    List<FirmViewModel> getAllFirms();

    void apply(String firmName, String username);

    List<TransactionPendingViewModel> getAllPendingTransactions(String name);

    List<TransactionServiceModel> getAllTransactions(String name);

    List<TransactionServiceModel> getAllSellTransactions(String name);

    List<TransactionServiceModel> getAllSellVoidTransactions(String name);

    List<TransactionServiceModel> getAllPurchaseTransactions(String name);

    List<TransactionServiceModel> getAllPurchaseVoidTransactions(String name);

    BigDecimal getAllTransactionsSum(String name);

    double getAllTransactionsQuantity(String name);

    BigDecimal getAllSellTransactionsSum(String name);

    double getAllSellTransactionsQuantity(String name);

    BigDecimal getAllPurchaseTransactionsSum(String name);

    double getAllPurchaseTransactionsQuantity(String name);

    BigDecimal getAllSellVoidTransactionsSum(String name);

    double getAllSellVoidTransactionsQuantity(String name);

    BigDecimal getAllPurchaseVoidTransactionsSum(String name);

    double getAllPurchaseVoidTransactionsQuantity(String name);
}
