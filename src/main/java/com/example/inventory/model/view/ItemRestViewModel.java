package com.example.inventory.model.view;

import com.example.inventory.model.entity.FirmEntity;

import java.math.BigDecimal;

public class ItemRestViewModel {

    private long id;
    private String name;
    private String barcode;
    private String groupName;
    private BigDecimal outgoingPrice;
    private String warehouseName;
    private String pictureUrl;
    private String description;

    public ItemRestViewModel() {
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

    public BigDecimal getOutgoingPrice() {
        return outgoingPrice;
    }

    public void setOutgoingPrice(BigDecimal outgoingPrice) {
        this.outgoingPrice = outgoingPrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
