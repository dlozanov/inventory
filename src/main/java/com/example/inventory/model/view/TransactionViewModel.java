package com.example.inventory.model.view;

import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.ItemEntity;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionViewModel {

    private long id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private UserEntity user;
    private UserEntity buyer;
    private FirmEntity firm;
    private LocalDateTime dateTime;
    private ItemEntity item;
    private BigDecimal price;
    private double quantity;
    private BigDecimal sum;
    private String address;

    public TransactionViewModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public FirmEntity getFirm() {
        return firm;
    }

    public void setFirm(FirmEntity firm) {
        this.firm = firm;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
