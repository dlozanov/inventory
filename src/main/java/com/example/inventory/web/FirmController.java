package com.example.inventory.web;

import com.example.inventory.exception.FirmNotFoundException;
import com.example.inventory.exception.UserRoleNoPermissionsException;
import com.example.inventory.model.binding.FirmRegistrationBindingModel;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.FirmServiceModel;
import com.example.inventory.model.service.UserRoleServiceModel;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.model.view.ItemRestViewModel;
import com.example.inventory.service.FirmService;
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
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/firms")
public class FirmController {

    private final FirmService firmService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public FirmController(FirmService firmService, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.firmService = firmService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/register")
    public String firmRegister(Model model, Principal principal){
        FirmViewModel firmViewModel = null;
        try{
            firmViewModel = firmService.getFirmFromUsername(principal.getName());
        } catch (FirmNotFoundException e) {
            
        }

        if(!model.containsAttribute("firmRegistrationBindingModel")){
            if(firmViewModel != null){
                userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
                model.addAttribute("firmRegistrationBindingModel", modelMapper.map(firmViewModel, FirmRegistrationBindingModel.class));
            } else {
                model.addAttribute("firmRegistrationBindingModel", new FirmRegistrationBindingModel());
            }
            model.addAttribute("firmExistsError", false);
        }

        if(firmViewModel != null){
            return "firm-edit";
        } else {
            return "firm-register";
        }
    }

    @GetMapping("/show/{name}")
    public String show(@PathVariable String name, Model model){
        model.addAttribute("firmData", firmService.getFirmByName(name));
        model.addAttribute("listItemsFromFirm", firmService.getAllItemsByFirmName(name)
                .stream().map(i -> modelMapper.map(i, ItemRestViewModel.class))
                .collect(Collectors.toList()));

        return "firm-show";
    }

    @PostMapping("/register")
    public String firmRegisterConfirm(@Valid FirmRegistrationBindingModel firmRegistrationBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      Principal principal){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("firmRegistrationBindingModel", firmRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.firmRegistrationBindingModel", bindingResult);

            return "redirect:/firms/register";
        }

        if(firmService.firmNameExists(firmRegistrationBindingModel.getName()) ||
                firmService.firmBulstatExists(firmRegistrationBindingModel.getBulstat())){
            redirectAttributes.addFlashAttribute("firmRegistrationBindingModel", firmRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("firmExistsError", true);

            return "redirect:/firms/register";
        }

        FirmServiceModel firmServiceModel = modelMapper.map(firmRegistrationBindingModel, FirmServiceModel.class);
        try {
            firmService.save(firmServiceModel, principal.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String firmEditConfirm(@Valid FirmRegistrationBindingModel firmRegistrationBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("firmRegistrationBindingModel", firmRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.firmRegistrationBindingModel", bindingResult);

            return "redirect:/firms/register";
        }

        FirmServiceModel firmServiceModel = modelMapper.map(firmRegistrationBindingModel, FirmServiceModel.class);
        try {
            firmService.edit(firmServiceModel, principal.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @PostMapping("/apply/{name}")
    public String applyConfirm(@PathVariable String name, Principal principal){

        firmService.apply(name, principal.getName());

        return "redirect:/home";
    }

}
