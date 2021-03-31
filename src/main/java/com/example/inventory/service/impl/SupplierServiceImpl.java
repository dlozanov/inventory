package com.example.inventory.service.impl;

import com.example.inventory.exception.FirmNotFoundException;
import com.example.inventory.exception.SupplierNotFoundException;
import com.example.inventory.exception.WarehouseNotFoundException;
import com.example.inventory.model.entity.FirmEntity;
import com.example.inventory.model.entity.SupplierEntity;
import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.SupplierServiceModel;
import com.example.inventory.repository.FirmRepository;
import com.example.inventory.repository.SupplierRepository;
import com.example.inventory.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final FirmRepository firmRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, FirmRepository firmRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.firmRepository = firmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(SupplierServiceModel supplierServiceModel, FirmServiceModel firmServiceModel) {
        SupplierEntity supplierEntity = modelMapper.map(supplierServiceModel, SupplierEntity.class);
        FirmEntity firmEntity = firmRepository.findByName(firmServiceModel.getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        supplierEntity.setFirm(firmEntity);
        supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierServiceModel findById(long id) {
        SupplierEntity supplierEntity = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Доставчика не може да бъде открит."));
        return modelMapper.map(supplierEntity, SupplierServiceModel.class);
    }

    @Override
    public void edit(SupplierServiceModel supplierServiceModel) {
        SupplierEntity supplierEntity = modelMapper.map(supplierServiceModel, SupplierEntity.class);
        SupplierEntity oldSupplier = supplierRepository.findById(supplierEntity.getId())
                .orElseThrow(() -> new WarehouseNotFoundException("Доставчика не може да бъде открит."));
        supplierEntity.setFirm(oldSupplier.getFirm());
        supplierRepository.save(supplierEntity);
    }
}
