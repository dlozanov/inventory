package com.example.inventory.web;

import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.UserRegistrationServiceModel;
import com.example.inventory.model.service.UserRoleServiceModel;
import com.example.inventory.service.FirmService;
import com.example.inventory.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final FirmService firmService;
    private final UserRoleService userRoleService;

    public HomeController(FirmService firmService, UserRoleService userRoleService) {
        this.firmService = firmService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal){
        model.addAttribute("registeredFirms", firmService.getAllFirms());
        List<UserRoleServiceModel> rolesByUsername = userRoleService.findRolesByUsername(principal.getName());
        for (UserRoleServiceModel u : rolesByUsername) {
            if(u.getRole() == UserRole.ADMIN){
                model.addAttribute("aplliedUsers", firmService.getAllUsers(principal.getName()));
                break;
            }
        }
        for (UserRoleServiceModel u : rolesByUsername) {
            if(u.getRole() == UserRole.ADMIN || u.getRole() == UserRole.MODERATOR){
                model.addAttribute("pendingPurchases", firmService.getAllPendingTransactions(principal.getName()));
                break;
            }
        }

        return "home";
    }

}
