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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ItemControllerTest {

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
    void testItemControllerGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/items-all"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("itemsAll"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testItemControllerGetAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/item-register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("vats"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("stockGroups"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("suppliers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("warehouses"));
    }

    @Test
    @WithMockUser(value = "denis", roles = {"USER", "ADMIN"})
    void testItemControllerPostRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items/add")
                .param("name", "Item1212 2")
                .param("barcode", "2131232133")
                .param("group", "Основни храни")
                .param("vat", "C")
                .param("incomingPrice", "2.46")
                .param("outgoingPrice", "2.85")
                .param("warehouse", "1")
                .param("supplier", "1")
                .param("quantity", "23232")
                .param("description", "Nov artikul")
                .with(csrf()));
    }
}
