package com.example.inventory.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "warehouses")
public class WarehouseEntity extends BaseEntity{

    private String town;
    private String address;
    private FirmEntity firm;

    public WarehouseEntity() {
    }

    @Column(name = "town", nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne
    public FirmEntity getFirm() {
        return firm;
    }

    public void setFirm(FirmEntity firm) {
        this.firm = firm;
    }
}
