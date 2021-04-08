package com.example.inventory;

import com.example.inventory.service.EmailService;
import com.example.inventory.service.StockGroupService;
import com.example.inventory.service.UserRoleService;
import com.example.inventory.service.VatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InventoryApplicationInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final StockGroupService stockGroupService;
    private final VatService vatService;
    private final EmailService emailService;

    public InventoryApplicationInit(UserRoleService userRoleService, StockGroupService stockGroupService, VatService vatService, EmailService emailService) {
        this.userRoleService = userRoleService;
        this.stockGroupService = stockGroupService;
        this.vatService = vatService;
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.seedUserRoles();
        vatService.seedVats();
        stockGroupService.seedStockGroups();
//        emailService.sendMessage("d_lozanov@abv.bg", "asd", "dsadasd");
    }
}
