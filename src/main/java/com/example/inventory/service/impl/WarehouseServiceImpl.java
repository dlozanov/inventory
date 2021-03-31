package com.example.inventory.service.impl;

import com.example.inventory.exception.FirmNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.exception.WarehouseNotFoundException;
import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.WarehouseEntity;
import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.WarehouseServiceModel;
import com.example.inventory.repository.FirmRepository;
import com.example.inventory.repository.WarehouseRepository;
import com.example.inventory.service.FirmService;
import com.example.inventory.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final FirmRepository firmRepository;
    private final FirmService firmService;
    private final ModelMapper modelMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, FirmRepository firmRepository, FirmService firmService, ModelMapper modelMapper) {
        this.warehouseRepository = warehouseRepository;
        this.firmRepository = firmRepository;
        this.firmService = firmService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(WarehouseServiceModel warehouseServiceModel, FirmServiceModel firmServiceModel) {
        WarehouseEntity warehouseEntity = modelMapper.map(warehouseServiceModel, WarehouseEntity.class);
        FirmEntity firmEntity = firmRepository.findByName(firmServiceModel.getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        warehouseEntity.setFirm(firmEntity);
        warehouseRepository.save(warehouseEntity);
    }

    @Override
    public WarehouseServiceModel findById(long id) {
        WarehouseEntity warehouseEntity = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Склада не може да бъде открит."));
        return modelMapper.map(warehouseEntity, WarehouseServiceModel.class);
    }

    @Override
    public void edit(WarehouseServiceModel warehouseServiceModel) {
        WarehouseEntity warehouseEntity = modelMapper.map(warehouseServiceModel, WarehouseEntity.class);
        WarehouseEntity oldWarehouse = warehouseRepository.findById(warehouseServiceModel.getId())
                .orElseThrow(() -> new WarehouseNotFoundException("Склада не може да бъде открит."));
        warehouseEntity.setFirm(oldWarehouse.getFirm());
        warehouseRepository.save(warehouseEntity);
    }
}
