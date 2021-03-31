package com.example.inventory.service;

import com.example.inventory.model.service.ItemServiceModel;

import java.io.IOException;

public interface ItemService {
    void save(ItemServiceModel itemServiceModel, String name) throws IOException;

    ItemServiceModel findById(long id);

    boolean hasEnoughQuantity(long id, double quantity);
}
