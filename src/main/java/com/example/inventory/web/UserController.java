package com.example.inventory.web;

import com.example.inventory.model.binding.UserEditBindingModel;
import com.example.inventory.model.binding.UserRegistrationBindingModel;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRegistrationServiceModel;
import com.example.inventory.service.UserRoleService;
import com.example.inventory.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public UserController(UserService userService, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        if(!model.containsAttribute("userRegistrationBindingModel")){
            model.addAttribute("userRegistrationBindingModel", new UserRegistrationBindingModel());
            model.addAttribute("usernameExist", false);
        }
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        if(!model.containsAttribute("userRegistrationBindingModel")){
            UserRegistrationServiceModel user = userService.findByUsername(principal.getName());
            UserRegistrationBindingModel userRegistrationBindingModel = modelMapper.map(user, UserRegistrationBindingModel.class);
            model.addAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
        }
        return "profile";
    }

    @GetMapping("/edit/{name}")
    public String edit(@PathVariable String name, Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN));
        UserRegistrationServiceModel userRegistrationServiceModel = userService.findByUsername(name);
        model.addAttribute("userEditBindingModel", modelMapper.map(userRegistrationServiceModel, UserEditBindingModel.class));
        return "user-edit";
    }

    @PostMapping("/register")
    public String registerAndLogin(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        if(userService.usernameExists(userRegistrationBindingModel.getUsername())){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("usernameExist", true);

            return "redirect:/users/register";
        }

        UserRegistrationServiceModel userRegistrationServiceModel = modelMapper.map(userRegistrationBindingModel, UserRegistrationServiceModel.class);

        userService.registerAndLogin(userRegistrationServiceModel);

        return "redirect:/home";
    }

    @PostMapping("/profile")
    public String profileConfirm(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);

            return "redirect:/users/profile";
        }

        UserRegistrationServiceModel userRegistrationServiceModel = modelMapper.map(userRegistrationBindingModel, UserRegistrationServiceModel.class);

        userService.saveProfile(userRegistrationServiceModel);

        return "redirect:/home";
    }

    @PostMapping("edit")
    public String editConfirm(@Valid UserEditBindingModel userEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel", bindingResult);
        }

        UserRegistrationServiceModel userRegistrationServiceModel = modelMapper.map(userEditBindingModel, UserRegistrationServiceModel.class);
        userService.edit(userRegistrationServiceModel, userEditBindingModel.getRole());

        return "redirect:/home";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                          String username,
                              RedirectAttributes attributes) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", username);

        return "redirect:/users/login";
    }
}
