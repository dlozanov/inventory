package com.example.inventory.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FirmRegistrationBindingModel {

    private String name;
    private String bulstat;
    private String town;
    private String address;
    private String ownerName;
    private String phone;
    private String email;
    private MultipartFile img;

    public FirmRegistrationBindingModel() {
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
    @Size(min = 9, max = 13, message = "Булстат трябва да е с дължина между 9 и 13 символа.")
    public String getBulstat() {
        return bulstat;
    }

    public void setBulstat(String bulstat) {
        this.bulstat = bulstat;
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

    @NotEmpty
    @Size(min = 3, max = 20, message = "Името на собственика трябва да е с дължина между 3 и 20 символа.")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Size(min = 6, max = 10, message = "Телефона трябва да е с дължина между 6 и 10 символа.")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
