package com.example.inventory.model.view;

import com.example.inventory.model.entity.*;

import java.math.BigDecimal;

public class ItemViewModel {

    private long id;
    private String name;
    private String barcode;
    private String groupName;
    private String vatName;
    private BigDecimal incomingPrice;
    private BigDecimal outgoingPrice;
    private FirmEntity firm;
    private String warehouseName;
    private String pictureUrl;
    private String supplierName;
    private double quantity;
    private String description;

    public ItemViewModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVatName() {
        return vatName;
    }

    public void setVatName(String vatName) {
        this.vatName = vatName;
    }

    public BigDecimal getIncomingPrice() {
        return incomingPrice;
    }

    public void setIncomingPrice(BigDecimal incomingPrice) {
        this.incomingPrice = incomingPrice;
    }

    public BigDecimal getOutgoingPrice() {
        return outgoingPrice;
    }

    public void setOutgoingPrice(BigDecimal outgoingPrice) {
        this.outgoingPrice = outgoingPrice;
    }

    public FirmEntity getFirm() {
        return firm;
    }

    public void setFirm(FirmEntity firm) {
        this.firm = firm;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
