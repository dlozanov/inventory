package com.example.inventory.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity{

    private String name;
    private String barcode;
    private StockGroupEntity group;
    private VatEntity vat;
    private BigDecimal incomingPrice;
    private BigDecimal outgoingPrice;
    private FirmEntity firm;
    private WarehouseEntity warehouse;
    private String pictureUrl;
    private SupplierEntity supplier;
    private double quantity;
    private String description;

    public ItemEntity() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "barcode", nullable = false)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @ManyToOne
    public StockGroupEntity getGroup() {
        return group;
    }

    public void setGroup(StockGroupEntity group) {
        this.group = group;
    }

    @ManyToOne
    public VatEntity getVat() {
        return vat;
    }

    public void setVat(VatEntity vat) {
        this.vat = vat;
    }

    @Column(name = "incoming_price", nullable = false)
    public BigDecimal getIncomingPrice() {
        return incomingPrice;
    }

    public void setIncomingPrice(BigDecimal incomingPrice) {
        this.incomingPrice = incomingPrice;
    }

    @Column(name = "outgoing_price", nullable = false)
    public BigDecimal getOutgoingPrice() {
        return outgoingPrice;
    }

    public void setOutgoingPrice(BigDecimal outgoingPrice) {
        this.outgoingPrice = outgoingPrice;
    }

    @ManyToOne
    public FirmEntity getFirm() {
        return firm;
    }

    public void setFirm(FirmEntity firm) {
        this.firm = firm;
    }

    @ManyToOne
    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "picture_url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @ManyToOne
    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    @Column(name = "quantity", nullable = false)
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
