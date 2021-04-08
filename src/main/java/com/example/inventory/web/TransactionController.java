package com.example.inventory.web;

import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.TransactionServiceModel;
import com.example.inventory.model.view.ItemViewModel;
import com.example.inventory.model.view.TransactionViewModel;
import com.example.inventory.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final ItemService itemService;
    private final TransactionService transactionService;
    private final UserRoleService userRoleService;
    private final FirmService firmService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public TransactionController(ItemService itemService, TransactionService transactionService, UserRoleService userRoleService, FirmService firmService, EmailService emailService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.transactionService = transactionService;
        this.userRoleService = userRoleService;
        this.firmService = firmService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/buy/{id}")
    public String buy(Model model, @PathVariable long id){
        model.addAttribute("itemData", itemService.findById(id));
        return "transaction-buy";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(Model model, @PathVariable long id, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("transactionData", transactionService.findById(id));
        return "transaction-confirm";
    }

    @GetMapping("/sell")
    public String sell(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("allItemsFromFirm", firmService
                .getAllItemsByFirmName(firmService.getFirmFromUsername(principal.getName()).getName())
                .stream().map(i -> modelMapper.map(i, ItemViewModel.class))
                .collect(Collectors.toList()));
        return "transaction-sell";
    }

    @GetMapping("/purchase")
    public String purchase(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("allItemsFromFirm", firmService
                .getAllItemsByFirmName(firmService.getFirmFromUsername(principal.getName()).getName())
                .stream().map(i -> modelMapper.map(i, ItemViewModel.class))
                .collect(Collectors.toList()));
        return "transaction-purchase";
    }

    @GetMapping("/void-sell")
    public String voidSell(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("allTransactions", firmService
                .getAllSellTransactions(principal.getName())
                .stream().map(i -> modelMapper.map(i, TransactionViewModel.class))
                .collect(Collectors.toList()));
        return "transaction-sell-void";
    }

    @GetMapping("/void-purchase")
    public String voidPurchase(Model model, Principal principal){
        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));
        model.addAttribute("allTransactions", firmService
                .getAllPurchaseTransactions(principal.getName())
                .stream().map(i -> modelMapper.map(i, TransactionViewModel.class))
                .collect(Collectors.toList()));
        return "transaction-purchase-void";
    }

    @PostMapping("/buy/{id}")
    public String buyConfirm(@PathVariable long id, @RequestParam(name = "inputQuantity") double quantity,
                             @RequestParam(name = "address") String address, Principal principal){

        transactionService.savePendingTransaction(id, quantity, address, principal.getName());

        return "redirect:/home";
    }

    @PostMapping("/confirm/{id}")
    public String confirmConfirm(@PathVariable long id, Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.confirmPendingTransaction(id, principal.getName());

        TransactionServiceModel byId = transactionService.findById(id);
        emailService.sendMessage(byId.getBuyer().getEmail(), "Purchase", "Поръчката ви е обработена и потвърдена.");

        return "redirect:/home";
    }

    @PostMapping("/decline/{id}")
    public String confirmDecline(@PathVariable long id, Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.declinePendingTransaction(id, principal.getName());

        TransactionServiceModel byId = transactionService.findById(id);
        emailService.sendMessage(byId.getBuyer().getEmail(), "Purchase", "Поръчката ви е отказана.");

        return "redirect:/home";
    }

    @PostMapping("/sell")
    public String confirmSell(@RequestParam(name = "item") long id,
                              @RequestParam(name = "inputQuantity") double quantity,
                              Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.sell(id, quantity, principal.getName());

        return "redirect:/home";
    }

    @PostMapping("/purchase")
    public String confirmPurchase(@RequestParam(name = "item") long id,
                              @RequestParam(name = "inputQuantity") double quantity,
                              Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.purchase(id, quantity, principal.getName());

        return "redirect:/home";
    }

    @PostMapping("/void-sell/{id}")
    public String confirmVoidSell(@PathVariable long id, Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.voidSell(id, principal.getName());

        return "redirect:/home";
    }

    @PostMapping("/void-purchase/{id}")
    public String confirmVoidPurchase(@PathVariable long id, Principal principal){

        userRoleService.getAccess(principal.getName(), List.of(UserRole.ADMIN, UserRole.MODERATOR));

        transactionService.voidPurchase(id, principal.getName());

        return "redirect:/home";
    }
}
