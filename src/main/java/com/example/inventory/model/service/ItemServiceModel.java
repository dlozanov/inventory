package com.example.inventory.model.service;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ItemServiceModel {

    private long id;
    private String name;
    private String barcode;
    private String group;
    private char vat;
    private BigDecimal incomingPrice;
    private BigDecimal outgoingPrice;
    private long warehouse;
    private MultipartFile pictureUrl;
    private long supplier;
    private double quantity;
    private String description;

    public ItemServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Името трябва да е с дължина между 3 и 20 символа.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Баркода трябва да е с дължина между 3 и 20 символа.")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @NotEmpty
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @NotEmpty
    public char getVat() {
        return vat;
    }

    public void setVat(char vat) {
        this.vat = vat;
    }

    @NotEmpty
    @DecimalMin("0")
    public BigDecimal getIncomingPrice() {
        return incomingPrice;
    }

    public void setIncomingPrice(BigDecimal incomingPrice) {
        this.incomingPrice = incomingPrice;
    }

    @NotEmpty
    @DecimalMin("0")
    public BigDecimal getOutgoingPrice() {
        return outgoingPrice;
    }

    public void setOutgoingPrice(BigDecimal outgoingPrice) {
        this.outgoingPrice = outgoingPrice;
    }

    @NotEmpty
    public long getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(long warehouse) {
        this.warehouse = warehouse;
    }

    public MultipartFile getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(MultipartFile pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @NotEmpty
    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    @NotEmpty
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
