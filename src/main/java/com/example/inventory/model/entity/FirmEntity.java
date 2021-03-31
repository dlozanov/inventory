package com.example.inventory.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "firms")
public class FirmEntity extends BaseEntity {

    private String name;
    private String bulstat;
    private String town;
    private String address;
    private String ownerName;
    private String phone;
    private String email;
    private String logoUrl;
    private List<WarehouseEntity> warehouses = new ArrayList<>();
    private List<ItemEntity> items = new ArrayList<>();
    private List<SupplierEntity> suppliers = new ArrayList<>();
    private List<UserEntity> users = new ArrayList<>();
    private List<TransactionEntity> transactions = new ArrayList<>();

    public FirmEntity() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "bulstat", nullable = false, unique = true)
    public String getBulstat() {
        return bulstat;
    }

    public void setBulstat(String bulstat) {
        this.bulstat = bulstat;
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

    @Column(name = "owner_name", nullable = false)
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @OneToMany(mappedBy = "firm")
    public List<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }

    public void addWarehouse(WarehouseEntity warehouseEntity){
        this.warehouses.add(warehouseEntity);
    }

    @OneToMany(mappedBy = "firm")
    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public void addItem(ItemEntity itemEntity){
        this.items.add(itemEntity);
    }

    @OneToMany(mappedBy = "firm")
    public List<SupplierEntity> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierEntity> suppliers) {
        this.suppliers = suppliers;
    }

    public void addSupplier(SupplierEntity supplierEntity){
        this.suppliers.add(supplierEntity);
    }

    @OneToMany(mappedBy = "firm")
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity userEntity){
        this.users.add(userEntity);
    }

    @OneToMany(mappedBy = "firm")
    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionEntity transactionEntity){
        this.transactions.add(transactionEntity);
    }
}
