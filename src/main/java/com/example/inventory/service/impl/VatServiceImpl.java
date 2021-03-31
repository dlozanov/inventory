package com.example.inventory.service.impl;

import com.example.inventory.model.entity.VatEntity;
import com.example.inventory.model.service.VatServiceModel;
import com.example.inventory.repository.VatRepository;
import com.example.inventory.service.VatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VatServiceImpl implements VatService {

    private final VatRepository vatRepository;
    private final ModelMapper modelMapper;

    public VatServiceImpl(VatRepository vatRepository, ModelMapper modelMapper) {
        this.vatRepository = vatRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedVats() {
        if(vatRepository.count() == 0){
            VatEntity vatA = new VatEntity();
            VatEntity vatB = new VatEntity();
            VatEntity vatC = new VatEntity();
            VatEntity vatD = new VatEntity();

            vatA.setLetter('А');
            vatB.setLetter('Б');
            vatC.setLetter('В');
            vatD.setLetter('Г');

            vatA.setPercent(0);
            vatB.setPercent(20);
            vatC.setPercent(20);
            vatD.setPercent(9);

            vatRepository.saveAll(List.of(vatA, vatB, vatC, vatD));
        }
    }

    @Override
    public List<VatServiceModel> getAllVats() {
        return vatRepository.findAll().stream().map(v -> modelMapper.map(v, VatServiceModel.class)).collect(Collectors.toList());
    }
}
