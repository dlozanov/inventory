package com.example.inventory.service.impl;

import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.SupplierNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.exception.WarehouseNotFoundException;
import com.example.inventory.model.entity.*;
import com.example.inventory.model.service.ItemServiceModel;
import com.example.inventory.repository.*;
import com.example.inventory.service.CloudinaryService;
import com.example.inventory.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StockGroupRepository stockGroupRepository;
    private final VatRepository vatRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public ItemServiceImpl(ItemRepository itemRepository, StockGroupRepository stockGroupRepository, VatRepository vatRepository, WarehouseRepository warehouseRepository, SupplierRepository supplierRepository, UserRepository userRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.itemRepository = itemRepository;
        this.stockGroupRepository = stockGroupRepository;
        this.vatRepository = vatRepository;
        this.warehouseRepository = warehouseRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void save(ItemServiceModel itemServiceModel, String name) throws IOException {
        MultipartFile multipartFile = itemServiceModel.getPictureUrl();
        String imageUrl = null;
        if(multipartFile != null && !multipartFile.isEmpty())
            imageUrl = cloudinaryService.uploadImage(multipartFile);

        ItemEntity itemEntity = modelMapper.map(itemServiceModel, ItemEntity.class);
        itemEntity.setPictureUrl(imageUrl);

        StockGroupEntity stockGroupEntity = stockGroupRepository.findByName(itemServiceModel.getGroup());
        VatEntity vatEntity = vatRepository.findByLetter(itemServiceModel.getVat());
        WarehouseEntity warehouseEntity = warehouseRepository.findById(itemServiceModel.getWarehouse())
                .orElseThrow(() -> new WarehouseNotFoundException("Склада не може да бъде открит."));
        SupplierEntity supplierEntity = supplierRepository.findById(itemServiceModel.getSupplier())
                .orElseThrow(() -> new SupplierNotFoundException("Доставчика не може да бъде открит."));

        itemEntity.setGroup(stockGroupEntity);
        itemEntity.setVat(vatEntity);
        itemEntity.setWarehouse(warehouseEntity);
        itemEntity.setSupplier(supplierEntity);

        UserEntity userEntity = userRepository
                .findByUsername(name)
                .orElseThrow(IllegalArgumentException::new);
        itemEntity.setFirm(userEntity.getFirm());

        itemRepository.save(itemEntity);
    }

    @Override
    public ItemServiceModel findById(long id) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Артикула не може да бъде открит."));
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setId(itemEntity.getId());
        itemServiceModel.setName(itemEntity.getName());
        itemServiceModel.setBarcode(itemEntity.getBarcode());
        itemServiceModel.setGroup(itemEntity.getGroup().getName());
        itemServiceModel.setVat(itemEntity.getVat().getLetter());
        itemServiceModel.setIncomingPrice(itemEntity.getIncomingPrice());
        itemServiceModel.setOutgoingPrice(itemEntity.getOutgoingPrice());
        itemServiceModel.setWarehouse(itemEntity.getWarehouse().getId());
        itemServiceModel.setSupplier(itemEntity.getSupplier().getId());
        itemServiceModel.setQuantity(itemEntity.getQuantity());
        itemServiceModel.setDescription(itemEntity.getDescription());
        return itemServiceModel;
    }

    @Override
    public boolean hasEnoughQuantity(long id, double quantity) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Артикула не може да бъде открит."));
        return itemEntity.getQuantity() >= quantity;
    }
}
