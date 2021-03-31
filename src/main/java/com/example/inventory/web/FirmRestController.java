package com.example.inventory.web;

import com.example.inventory.model.view.ItemRestViewModel;
import com.example.inventory.service.FirmService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/firms")
@RestController
public class FirmRestController {

    private final FirmService firmService;
    private final ModelMapper modelMapper;

    public FirmRestController(FirmService firmService, ModelMapper modelMapper) {
        this.firmService = firmService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/items/api/{name}")
    public ResponseEntity<List> findAllItems(@PathVariable String name){
        List items = firmService.getAllItemsByFirmName(name)
                .stream().map(i -> modelMapper.map(i, ItemRestViewModel.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(items);
    }

}
