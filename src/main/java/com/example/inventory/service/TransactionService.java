package com.example.inventory.service;

import com.example.inventory.model.service.TransactionServiceModel;

public interface TransactionService {
    void savePendingTransaction(long itemId, double quantity, String address, String username);

    TransactionServiceModel findById(long id);

    void confirmPendingTransaction(long id, String username);

    void sell(long id, double quantity, String name);

    void purchase(long id, double quantity, String name);

    void voidSell(long id, String name);

    void voidPurchase(long id, String name);

    void declinePendingTransaction(long id, String name);
}
