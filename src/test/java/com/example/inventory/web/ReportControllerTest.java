package com.example.inventory.web;

import com.example.inventory.repository.*;
import com.example.inventory.service.StockGroupService;
import com.example.inventory.service.UserRoleService;
import com.example.inventory.service.VatService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private VatService vatService;
    @Autowired
    private StockGroupService stockGroupService;
    @Autowired
    private FirmRepository firmRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private StockGroupRepository stockGroupRepository;
    @Autowired
    private VatRepository vatRepository;

    private FirmTestData firmTestData;

    @BeforeEach
    public void init(){
        firmTestData = new FirmTestData(userRoleService, vatService, stockGroupService, firmRepository,
                itemRepository, supplierRepository, transactionRepository, warehouseRepository,
                userRepository, userRoleRepository, stockGroupRepository, vatRepository);
        firmTestData.init();
    }

    @AfterEach
    public void cleanUp(){
        firmTestData.cleanUp();
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/inventory"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-inventory"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("allItems"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGetAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transSum"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transQty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportMessage"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGetSells() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/sells"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transSum"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transQty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportMessage"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGetPurchases() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/purchases"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transSum"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transQty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportMessage"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGetSellVoid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/sells-void"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transSum"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transQty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportMessage"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testReportControllerGetPurchasesVoid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/purchases-void"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transSum"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transQty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportMessage"));
    }
}
