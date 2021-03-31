package com.example.inventory.model.entity;

import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity{

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

    public TransactionEntity() {
    }

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
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

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    @ManyToOne
    public FirmEntity getFirm() {
        return firm;
    }

    public void setFirm(FirmEntity firm) {
        this.firm = firm;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @ManyToOne
    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "quantity", nullable = false)
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Column(name = "sum", nullable = false)
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
