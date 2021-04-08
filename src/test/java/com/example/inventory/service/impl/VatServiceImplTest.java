package com.example.inventory.service.impl;

import com.example.inventory.model.entity.VatEntity;
import com.example.inventory.model.service.VatServiceModel;
import com.example.inventory.repository.VatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VatServiceImplTest {

    private VatEntity vat1, vat2;

    @Mock
    VatRepository mockVatRepository;

    private VatServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        vat1 = new VatEntity();
        vat1.setLetter('A');
        vat1.setPercent(20);

        vat2 = new VatEntity();
        vat2.setLetter('B');
        vat2.setPercent(10);

        serviceToTest = new VatServiceImpl(mockVatRepository, new ModelMapper());
    }

    @Test
    public void testFindAllVats(){
        when(mockVatRepository.findAll()).thenReturn(List.of(vat1, vat2));
        List<VatServiceModel> vats = serviceToTest.getAllVats();

        Assertions.assertEquals(2, vats.size());

        VatServiceModel model1 = vats.get(0);
        VatServiceModel model2 = vats.get(1);

        // verify model1
        Assertions.assertEquals(vat1.getLetter(), model1.getLetter());
        Assertions.assertEquals(vat1.getPercent(), model1.getPercent());

        // verify model2
        Assertions.assertEquals(vat2.getLetter(), model2.getLetter());
        Assertions.assertEquals(vat2.getPercent(), model2.getPercent());
    }

}
