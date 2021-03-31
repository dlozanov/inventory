package com.example.inventory.service;

import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.SupplierServiceModel;

import java.util.List;

public interface SupplierService {
    void save(SupplierServiceModel supplierServiceModel, FirmServiceModel firmServiceModel);

    SupplierServiceModel findById(long id);

    void edit(SupplierServiceModel supplierServiceModel);
}
