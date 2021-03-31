package com.example.inventory.service;

import com.example.inventory.model.service.StockGroupServiceModel;

import java.util.List;

public interface StockGroupService {
    void seedStockGroups();

    List<StockGroupServiceModel> getAllStockGroups();
}
