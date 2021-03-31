package com.example.inventory.web;

import com.example.inventory.model.binding.WarehouseRegistrationBindingModel;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.WarehouseServiceModel;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.service.FirmService;
import com.example.inventory.service.UserRoleService;
import com.example.inventory.service.WarehouseService;
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
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final FirmService firmService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public WarehouseController(WarehouseService warehouseService, FirmService firmService, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.warehouseService = warehouseService;
        this.firmService = firmService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/get")
    public String getAll(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("warehousesAll", firmService.getAllWarhouses(principal.getName()));
        return "/warehouse-all";
    }

    @GetMapping("/add")
    public String add(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        if(!model.containsAttribute("warehouseRegistrationBindingModel")){
            model.addAttribute("warehouseRegistrationBindingModel", new WarehouseRegistrationBindingModel());
        }
        return "warehouse-register";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        WarehouseRegistrationBindingModel warehouseRegistrationBindingModel = modelMapper.map(warehouseService.findById(id), WarehouseRegistrationBindingModel.class);
        model.addAttribute("warehouseRegistrationBindingModel", warehouseRegistrationBindingModel);
        return "warehouse-edit";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid WarehouseRegistrationBindingModel warehouseRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("warehouseRegistrationBindingModel", warehouseRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.warehouseRegistrationBindingModel", bindingResult);

            return "redirect:/warehouses/add";
        }

        FirmViewModel firm = firmService.getFirmFromUsername(principal.getName());
        if(firm == null){
            return "redirect:/firms/register";
        }

        warehouseService.add(modelMapper.map(warehouseRegistrationBindingModel, WarehouseServiceModel.class),
                modelMapper.map(firm, FirmServiceModel.class));

        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String editConfirm(@Valid WarehouseRegistrationBindingModel warehouseRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("warehouseRegistrationBindingModel", warehouseRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.warehouseRegistrationBindingModel", bindingResult);

            return "redirect:/warehouses/edit/" + warehouseRegistrationBindingModel.getId();
        }

        WarehouseServiceModel warehouseServiceModel = modelMapper.map(warehouseRegistrationBindingModel, WarehouseServiceModel.class);

        warehouseService.edit(warehouseServiceModel);

        return "redirect:/home";
    }

}
