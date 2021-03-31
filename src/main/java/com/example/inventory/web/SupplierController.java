package com.example.inventory.web;

import com.example.inventory.model.binding.SupplierRegistrationBindingModel;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.SupplierServiceModel;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.service.FirmService;
import com.example.inventory.service.SupplierService;
import com.example.inventory.service.UserRoleService;
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
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final FirmService firmService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public SupplierController(SupplierService supplierService, FirmService firmService, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.supplierService = supplierService;
        this.firmService = firmService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/get")
    public String getAll(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("suppliersAll", firmService.getAllSuppliers(principal.getName()));
        return "/suppliers-all";
    }

    @GetMapping("/add")
    public String add(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        if(!model.containsAttribute("supplierRegistrationBindingModel")){
            model.addAttribute("supplierRegistrationBindingModel", new SupplierRegistrationBindingModel());
        }
        return "/supplier-register";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        SupplierServiceModel supplierServiceModel = supplierService.findById(id);
        SupplierRegistrationBindingModel supplierRegistrationBindingModel = modelMapper.map(supplierServiceModel, SupplierRegistrationBindingModel.class);
        model.addAttribute("supplierRegistrationBindingModel", supplierRegistrationBindingModel);
        return "supplier-edit";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid  SupplierRegistrationBindingModel supplierRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("supplierRegistrationBindingModel", supplierRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplierRegistrationBindingModel", bindingResult);

            return "redirect:/suppliers/add";
        }

        FirmViewModel firm = firmService.getFirmFromUsername(principal.getName());
        if(firm == null){
            return "redirect:/firms/register";
        }

        supplierService.save(modelMapper.map(supplierRegistrationBindingModel, SupplierServiceModel.class),
                modelMapper.map(firm, FirmServiceModel.class));

        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String editConfirm(@Valid  SupplierRegistrationBindingModel supplierRegistrationBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("supplierRegistrationBindingModel", supplierRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplierRegistrationBindingModel", bindingResult);

            return "redirect:/suppliers/edit" + supplierRegistrationBindingModel.getId();
        }

        supplierService.edit(modelMapper.map(supplierRegistrationBindingModel, SupplierServiceModel.class));

        return "redirect:/home";
    }
}
