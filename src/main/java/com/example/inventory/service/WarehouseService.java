package com.example.inventory.service;

import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.WarehouseServiceModel;
import com.example.inventory.model.view.FirmViewModel;

import java.util.List;

public interface WarehouseService {
    void add(WarehouseServiceModel warehouseServiceModel, FirmServiceModel firmServiceModel);

    WarehouseServiceModel findById(long id);

    void edit(WarehouseServiceModel warehouseServiceModel);
}
