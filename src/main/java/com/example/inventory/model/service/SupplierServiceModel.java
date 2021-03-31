package com.example.inventory.model.service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SupplierServiceModel {

    private long id;
    private String name;
    private String email;
    private String phone;

    public SupplierServiceModel() {
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
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 6, max = 40, message = "Телефона трябва да е с дължина между 6 и 10 символа.")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
