package com.example.inventory.service.impl;

import com.example.inventory.model.entity.StockGroupEntity;
import com.example.inventory.model.service.StockGroupServiceModel;
import com.example.inventory.repository.StockGroupRepository;
import com.example.inventory.service.StockGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockGroupServiceImpl implements StockGroupService {

    private final StockGroupRepository stockGroupRepository;
    private final ModelMapper modelMapper;

    public StockGroupServiceImpl(StockGroupRepository stockGroupRepository, ModelMapper modelMapper) {
        this.stockGroupRepository = stockGroupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedStockGroups() {
        if(stockGroupRepository.count() == 0){
            seedGroup("Плодове и зеленчуци");
            seedGroup("Основни храни");
            seedGroup("Хлебни изделия");
            seedGroup("Млечни продукти");
            seedGroup("Месо и колбаси");
            seedGroup("Консерви и риба");
            seedGroup("Кафе, чай, напитки");
            seedGroup("Захарни изделия");
            seedGroup("Домашни потреби");
            seedGroup("Перилни препарати");
            seedGroup("Нехранителни стоки");
            seedGroup("Алкохол и цигари");
            seedGroup("Производствени стоки");
            seedGroup("Други");
        }
    }

    @Override
    public List<StockGroupServiceModel> getAllStockGroups() {
        return stockGroupRepository.findAll().stream().map(sg -> modelMapper.map(sg, StockGroupServiceModel.class))
                .collect(Collectors.toList());
    }

    private void seedGroup(String groupName){
        StockGroupEntity stockGroupEntity = new StockGroupEntity();
        stockGroupEntity.setName(groupName);
        stockGroupRepository.save(stockGroupEntity);
    }
}
