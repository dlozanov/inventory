package com.example.inventory.web;

import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.service.FirmService;
import com.example.inventory.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final FirmService firmService;
    private final UserRoleService userRoleService;

    public ReportController(FirmService firmService, UserRoleService userRoleService) {
        this.firmService = firmService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("inventory")
    public String inventory(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("allItems", firmService.getAllItems(principal.getName()));
        return "report-inventory";
    }

    @GetMapping("all")
    public String all(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactions", firmService.getAllTransactions(principal.getName()));
        model.addAttribute("transSum", firmService.getAllTransactionsSum(principal.getName()).doubleValue());
        model.addAttribute("transQty", firmService.getAllTransactionsQuantity(principal.getName()));
        model.addAttribute("reportMessage", "Всички транзакции");
        return "report-all";
    }

    @GetMapping("sells")
    public String sells(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactions", firmService.getAllSellTransactions(principal.getName()));
        model.addAttribute("transSum", firmService.getAllSellTransactionsSum(principal.getName()));
        model.addAttribute("transQty", firmService.getAllSellTransactionsQuantity(principal.getName()));
        model.addAttribute("reportMessage", "Продажби");
        return "report-all";
    }

    @GetMapping("purchases")
    public String purchases(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactions", firmService.getAllPurchaseTransactions(principal.getName()));
        model.addAttribute("transSum", firmService.getAllPurchaseTransactionsSum(principal.getName()));
        model.addAttribute("transQty", firmService.getAllPurchaseTransactionsQuantity(principal.getName()));
        model.addAttribute("reportMessage", "Покупки");
        return "report-all";
    }

    @GetMapping("sells-void")
    public String sellsVoid(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactions", firmService.getAllSellVoidTransactions(principal.getName()));
        model.addAttribute("transSum", firmService.getAllSellVoidTransactionsSum(principal.getName()));
        model.addAttribute("transQty", firmService.getAllSellVoidTransactionsQuantity(principal.getName()));
        model.addAttribute("reportMessage", "Върнати продажби");
        return "report-all";
    }

    @GetMapping("purchases-void")
    public String purchasesVoid(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactions", firmService.getAllPurchaseVoidTransactions(principal.getName()));
        model.addAttribute("transSum", firmService.getAllPurchaseVoidTransactionsSum(principal.getName()));
        model.addAttribute("transQty", firmService.getAllPurchaseVoidTransactionsQuantity(principal.getName()));
        model.addAttribute("reportMessage", "Върнати покупки");
        return "report-all";
    }

}
