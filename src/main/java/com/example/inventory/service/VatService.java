package com.example.inventory.service;

import com.example.inventory.model.service.VatServiceModel;

import java.util.List;

public interface VatService {
    void seedVats();

    List<VatServiceModel> getAllVats();
}
