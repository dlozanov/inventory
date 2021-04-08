package com.example.inventory.service.impl;

import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.model.entity.StockGroupEntity;
import com.example.inventory.model.service.StockGroupServiceModel;
import com.example.inventory.repository.StockGroupRepository;
import org.hibernate.hql.internal.classic.GroupByParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StockGroupServiceImplTest {

    private StockGroupEntity stock1, stock2;

    @Mock
    StockGroupRepository mockStockGroupRepository;

    StockGroupServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        stock1 = new StockGroupEntity();
        stock1.setName("Stock group 1");

        stock2 = new StockGroupEntity();
        stock2.setName("Stock group 2");

        serviceToTest = new StockGroupServiceImpl(mockStockGroupRepository, new ModelMapper());
    }

    @Test
    void testStockGroupFindAll(){
        Mockito.when(mockStockGroupRepository.findAll()).thenReturn(List.of(stock1, stock2));
        List<StockGroupServiceModel> list = serviceToTest.getAllStockGroups();

        StockGroupServiceModel model1 = list.get(0);
        StockGroupServiceModel model2 = list.get(1);

        Assertions.assertEquals(stock1.getName(), model1.getName());
        Assertions.assertEquals(stock2.getName(), model2.getName());
    }

}
