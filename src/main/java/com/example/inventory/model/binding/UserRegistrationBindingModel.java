package com.example.inventory.model.binding;

import com.example.inventory.model.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegistrationBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String email;
    private String phone;

    public UserRegistrationBindingModel() {
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Името трябва да е с дължина между 3 и 20 символа.")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    @Size(min = 6, max = 20, message = "Паролата трябва да е с дължина между 6 и 20 символа.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty
    @Size(min = 3, max = 30, message = "Името трябва да е с дължина между 3 и 30 символа.")
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 6, max = 10, message = "Телефона трябва да е с дължина между 6 и 10 символа.")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
