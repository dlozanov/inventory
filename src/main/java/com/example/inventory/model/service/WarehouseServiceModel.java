package com.example.inventory.model.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class WarehouseServiceModel {

    private long id;
    private String town;
    private String address;

    public WarehouseServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Града трябва да е с дължина между 3 и 20 символа.")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Адреса трябва да е с дължина между 3 и 20 символа.")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
