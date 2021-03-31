package com.example.inventory.model.view;

import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.ItemEntity;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionPendingViewModel {

    private long id;
    private ItemEntity item;
    private BigDecimal price;
    private double quantity;
    private BigDecimal sum;
    private String address;

    public TransactionPendingViewModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
