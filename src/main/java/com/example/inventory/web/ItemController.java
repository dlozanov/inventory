package com.example.inventory.web;

import com.example.inventory.model.binding.ItemRegistrationBindingModel;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.*;
import com.example.inventory.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final FirmService firmService;
    private final VatService vatService;
    private final StockGroupService stockGroupService;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public ItemController(ItemService itemService, FirmService firmService, VatService vatService, StockGroupService stockGroupService, WarehouseService warehouseService, SupplierService supplierService, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.itemService = itemService;
        this.firmService = firmService;
        this.vatService = vatService;
        this.stockGroupService = stockGroupService;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/get")
    public String getAll(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("itemsAll", firmService.getAllItems(principal.getName()));
        return "/items-all";
    }

    @GetMapping("/add")
    public String add(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        List<VatServiceModel> vats = vatService.getAllVats();
        List<StockGroupServiceModel> stockGroups = stockGroupService.getAllStockGroups();
        List<SupplierServiceModel> suppliers = firmService.getAllSuppliers(principal.getName());
        List<WarehouseServiceModel> warehouses = firmService.getAllWarhouses(principal.getName());
        if(!model.containsAttribute("itemRegistrationBindingModel")){
            model.addAttribute("itemRegistrationBindingModel", new ItemRegistrationBindingModel());
        }
        model.addAttribute("vats", vats);
        model.addAttribute("stockGroups", stockGroups);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("warehouses", warehouses);

        return "/item-register";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        List<VatServiceModel> vats = vatService.getAllVats();
        List<StockGroupServiceModel> stockGroups = stockGroupService.getAllStockGroups();
        List<SupplierServiceModel> suppliers = firmService.getAllSuppliers(principal.getName());
        List<WarehouseServiceModel> warehouses = firmService.getAllWarhouses(principal.getName());
        ItemServiceModel itemServiceModel = itemService.findById(id);
        if(itemServiceModel != null){
            model.addAttribute("itemRegistrationBindingModel", modelMapper.map(itemServiceModel, ItemRegistrationBindingModel.class));
        }
        model.addAttribute("vats", vats);
        model.addAttribute("stockGroups", stockGroups);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("warehouses", warehouses);

        return "/item-edit";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ItemRegistrationBindingModel itemRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("itemRegistrationBindingModel", itemRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemRegistrationBindingModel", bindingResult);
        }

        ItemServiceModel itemServiceModel = modelMapper.map(itemRegistrationBindingModel, ItemServiceModel.class);
        try {
            itemService.save(itemServiceModel, principal.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String editConfirm(@Valid ItemRegistrationBindingModel itemRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("itemRegistrationBindingModel", itemRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemRegistrationBindingModel", bindingResult);
        }

        ItemServiceModel itemServiceModel = modelMapper.map(itemRegistrationBindingModel, ItemServiceModel.class);
        try {
            itemService.save(itemServiceModel, principal.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

}
