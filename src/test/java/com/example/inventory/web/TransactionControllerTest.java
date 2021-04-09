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
public class TransactionControllerTest {

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
    void testTransactionControllerGetSell() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/sell"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transaction-sell"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testTransactionControllerGetPurchase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/purchase"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transaction-purchase"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testTransactionControllerGetSellVoid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/void-sell"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transaction-sell-void"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testTransactionControllerGetPurchaseVoid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/void-purchase"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transaction-purchase-void"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testTransactionControllerPostRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/warehouses/add")
                .param("town", "Sofia")
                .param("address", "Centar")
                .with(csrf()))
        .
        andExpect(status().is3xxRedirection());
    }
}
