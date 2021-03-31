package com.example.inventory.service.impl;

import com.example.inventory.exception.FirmNotFoundException;
import com.example.inventory.exception.TransactionNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.model.entity.*;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;
import com.example.inventory.model.entity.enums.UserRole;
import com.example.inventory.model.service.*;
import com.example.inventory.model.view.FirmViewModel;
import com.example.inventory.model.view.ItemViewModel;
import com.example.inventory.model.view.TransactionPendingViewModel;
import com.example.inventory.repository.FirmRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.UserRoleRepository;
import com.example.inventory.service.CloudinaryService;
import com.example.inventory.service.FirmService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;
    private final InventoryUserService inventoryUserService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public FirmServiceImpl(FirmRepository firmRepository, InventoryUserService inventoryUserService, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.firmRepository = firmRepository;
        this.inventoryUserService = inventoryUserService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean firmNameExists(String name) {
        return firmRepository.findByName(name).isPresent();
    }

    @Override
    public boolean firmBulstatExists(String bulstat) {
        return firmRepository.findByBulstat(bulstat).isPresent();
    }

    @Override
    public void save(FirmServiceModel firmServiceModel, String name) throws IOException {
        MultipartFile multipartFile = firmServiceModel.getImg();
        String imageUrl = null;
        if(multipartFile != null && !multipartFile.isEmpty())
            imageUrl = cloudinaryService.uploadImage(multipartFile);

        FirmEntity firmEntity = modelMapper.map(firmServiceModel, FirmEntity.class);
        firmEntity.setLogoUrl(imageUrl);
        firmRepository.save(firmEntity);

        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRole.ADMIN).orElseThrow();

        UserEntity userEntity = userRepository.findByUsername(name)
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открита."));
        userEntity.setFirm(firmEntity);
        userEntity.setRoles(List.of(userRoleEntity));
        userRepository.save(userEntity);

        UserDetails principal = inventoryUserService.loadUserByUsername(userEntity.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public FirmViewModel getFirmFromUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открит."));
        if(userEntity.getFirm() != null)
            return modelMapper.map(userEntity.getFirm(), FirmViewModel.class);
        else
            throw new FirmNotFoundException("Фирмата не може да бъде намерена.");
    }

    @Override
    public FirmViewModel getFirmByName(String name) {
        FirmEntity firmEntity = firmRepository.findByName(name)
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        return modelMapper.map(firmEntity, FirmViewModel.class);
    }

    @Override
    public void edit(FirmServiceModel firmServiceModel, String name) throws IOException {
        MultipartFile multipartFile = firmServiceModel.getImg();
        String imageUrl = null;
        if(multipartFile != null && !multipartFile.isEmpty())
            imageUrl = cloudinaryService.uploadImage(multipartFile);

        FirmEntity oldFirm = firmRepository.findByName(firmServiceModel.getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        if(imageUrl == null)
            imageUrl = oldFirm.getLogoUrl();

        FirmEntity firmEntity = modelMapper.map(firmServiceModel, FirmEntity.class);
        firmEntity.setId(oldFirm.getId());
        firmEntity.setLogoUrl(imageUrl);
        firmRepository.save(firmEntity);
    }

    @Override
    public List<FirmViewModel> getAllFirms() {
        return firmRepository.findAll().stream().map(f -> modelMapper.map(f, FirmViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void apply(String firmName, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Потребителя не може да бъде открит."));
        FirmEntity firmEntity = firmRepository.findByName(firmName)
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        userEntity.setFirm(firmEntity);
        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public List<WarehouseServiceModel> getAllWarhouses(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getWarehouses());
        if(firmEntity.getWarehouses() != null)
            return firmEntity.getWarehouses().stream().map(w -> modelMapper.map(w, WarehouseServiceModel.class)).collect(Collectors.toList());
        else
            return new ArrayList<WarehouseServiceModel>();
    }

    @Transactional
    @Override
    public List<ItemViewModel> getAllItems(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getItems());
        if(firmEntity.getItems() != null){
            List<ItemViewModel> list = new ArrayList<>();
            for(ItemEntity i : firmEntity.getItems()){
                ItemViewModel itemViewModel = modelMapper.map(i, ItemViewModel.class);
                itemViewModel.setGroupName(i.getGroup().getName());
                itemViewModel.setVatName(i.getVat().getLetter() + " - " + i.getVat().getPercent());
                itemViewModel.setWarehouseName(i.getWarehouse().getTown() + " " + i.getWarehouse().getAddress());
                itemViewModel.setSupplierName(i.getSupplier().getName());
                list.add(itemViewModel);
            }
            return list;
        }
        else
            return new ArrayList<ItemViewModel>();
    }

    @Transactional
    @Override
    public List<ItemViewModel> getAllItemsByFirmName(String name) {
        FirmEntity firmEntity = firmRepository.findByName(name)
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getItems());
        if(firmEntity.getItems() != null){
            List<ItemViewModel> list = new ArrayList<>();
            for(ItemEntity i : firmEntity.getItems()){
                ItemViewModel itemViewModel = modelMapper.map(i, ItemViewModel.class);
                itemViewModel.setGroupName(i.getGroup().getName());
                itemViewModel.setVatName(i.getVat().getLetter() + " - " + i.getVat().getPercent());
                itemViewModel.setWarehouseName(i.getWarehouse().getTown() + " " + i.getWarehouse().getAddress());
                itemViewModel.setSupplierName(i.getSupplier().getName());
                if(itemViewModel.getPictureUrl() == null)
                    itemViewModel.setPictureUrl("");
                list.add(itemViewModel);
            }
            return list;
        }
        else
            return new ArrayList<ItemViewModel>();
    }

    @Transactional
    @Override
    public List<SupplierServiceModel> getAllSuppliers(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getSuppliers());
        if(firmEntity.getSuppliers() != null)
            return firmEntity.getSuppliers().stream().map(s -> modelMapper.map(s, SupplierServiceModel.class)).collect(Collectors.toList());
        else
            return new ArrayList<SupplierServiceModel>();
    }

    @Transactional
    @Override
    public List<UserRegistrationServiceModel> getAllUsers(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getUsers());
        if(firmEntity.getUsers() != null)
            return firmEntity.getUsers().stream().map(u -> modelMapper.map(u, UserRegistrationServiceModel.class)).collect(Collectors.toList());
        else
            return new ArrayList<UserRegistrationServiceModel>();
    }

    @Transactional
    @Override
    public List<TransactionPendingViewModel> getAllPendingTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .filter(t -> t.getTransactionType() == TransactionType.SELL &&
                            t.getTransactionStatus() == TransactionStatus.PENDING)
                    .map(u -> modelMapper.map(u, TransactionPendingViewModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionPendingViewModel>();
    }

    @Transactional
    @Override
    public List<TransactionServiceModel> getAllTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .map(f -> modelMapper.map(f, TransactionServiceModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionServiceModel>();
    }

    @Transactional
    @Override
    public List<TransactionServiceModel> getAllSellTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .filter(t -> t.getTransactionType() == TransactionType.SELL
                        && t.getTransactionStatus() == TransactionStatus.APPROVED)
                    .map(f -> modelMapper.map(f, TransactionServiceModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionServiceModel>();
    }

    @Transactional
    @Override
    public List<TransactionServiceModel> getAllSellVoidTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .filter(t -> t.getTransactionType() == TransactionType.VOID_SELL
                            && t.getTransactionStatus() == TransactionStatus.APPROVED)
                    .map(f -> modelMapper.map(f, TransactionServiceModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionServiceModel>();
    }

    @Transactional
    @Override
    public List<TransactionServiceModel> getAllPurchaseTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .filter(t -> t.getTransactionType() == TransactionType.PURCHASE
                            && t.getTransactionStatus() == TransactionStatus.APPROVED)
                    .map(f -> modelMapper.map(f, TransactionServiceModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionServiceModel>();
    }

    @Transactional
    @Override
    public List<TransactionServiceModel> getAllPurchaseVoidTransactions(String name) {
        FirmEntity firmEntity = firmRepository.findByName(getFirmFromUsername(name).getName())
                .orElseThrow(() -> new FirmNotFoundException("Фирмата не може да бъде открита."));
        Hibernate.initialize(firmEntity.getTransactions());
        if(firmEntity.getTransactions() != null)
            return firmEntity.getTransactions().stream()
                    .filter(t -> t.getTransactionType() == TransactionType.VOID_PURCHASE
                            && t.getTransactionStatus() == TransactionStatus.APPROVED)
                    .map(f -> modelMapper.map(f, TransactionServiceModel.class))
                    .collect(Collectors.toList());
        else
            return new ArrayList<TransactionServiceModel>();
    }

    @Transactional
    @Override
    public BigDecimal getAllTransactionsSum(String name) {
        List<TransactionServiceModel> trans = getAllTransactions(name);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED
                            || t.getTransactionStatus() == TransactionStatus.VOID){
                        sum = sum.add(t.getSum());
                    }
                    break;
                case PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED
                            || t.getTransactionStatus() == TransactionStatus.VOID)
                        sum = sum.subtract(t.getSum());
                    break;
                case VOID_SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        sum = sum.subtract(t.getSum());
                    break;
                case VOID_PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        sum = sum.add(t.getSum());
                    break;
            }
        }
        return sum;
    }

    @Transactional
    @Override
    public double getAllTransactionsQuantity(String name) {
        List<TransactionServiceModel> trans = getAllTransactions(name);
        double quantity = 0;
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED
                            || t.getTransactionStatus() == TransactionStatus.VOID)
                        quantity += t.getQuantity();
                    break;
                case PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED
                            || t.getTransactionStatus() == TransactionStatus.VOID)
                        quantity -= t.getQuantity();
                    break;
                case VOID_SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity -= t.getQuantity();
                    break;
                case VOID_PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity += t.getQuantity();
                    break;
            }
        }
        return quantity;
    }

    @Transactional
    @Override
    public BigDecimal getAllSellTransactionsSum(String name) {
        List<TransactionServiceModel> trans = getAllSellTransactions(name);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED){
                        sum = sum.add(t.getSum());
                    }
                    break;
            }
        }
        return sum;
    }

    @Transactional
    @Override
    public double getAllSellTransactionsQuantity(String name) {
        List<TransactionServiceModel> trans = getAllSellTransactions(name);
        double quantity = 0;
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity += t.getQuantity();
                    break;
            }
        }
        return quantity;
    }

    @Transactional
    @Override
    public BigDecimal getAllPurchaseTransactionsSum(String name) {
        List<TransactionServiceModel> trans = getAllPurchaseTransactions(name);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED){
                        sum = sum.add(t.getSum());
                    }
                    break;
            }
        }
        return sum;
    }

    @Transactional
    @Override
    public double getAllPurchaseTransactionsQuantity(String name) {
        List<TransactionServiceModel> trans = getAllPurchaseTransactions(name);
        double quantity = 0;
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity += t.getQuantity();
                    break;
            }
        }
        return quantity;
    }

    @Transactional
    @Override
    public BigDecimal getAllSellVoidTransactionsSum(String name) {
        List<TransactionServiceModel> trans = getAllSellVoidTransactions(name);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case VOID_SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED){
                        sum = sum.add(t.getSum());
                    }
                    break;
            }
        }
        return sum;
    }

    @Transactional
    @Override
    public double getAllSellVoidTransactionsQuantity(String name) {
        List<TransactionServiceModel> trans = getAllSellVoidTransactions(name);
        double quantity = 0;
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case VOID_SELL:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity += t.getQuantity();
                    break;
            }
        }
        return quantity;
    }

    @Transactional
    @Override
    public BigDecimal getAllPurchaseVoidTransactionsSum(String name) {
        List<TransactionServiceModel> trans = getAllPurchaseVoidTransactions(name);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case VOID_PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED){
                        sum = sum.add(t.getSum());
                    }
                    break;
            }
        }
        return sum;
    }

    @Transactional
    @Override
    public double getAllPurchaseVoidTransactionsQuantity(String name) {
        List<TransactionServiceModel> trans = getAllPurchaseVoidTransactions(name);
        double quantity = 0;
        for (TransactionServiceModel t : trans){
            switch (t.getTransactionType()){
                case VOID_PURCHASE:
                    if(t.getTransactionStatus() == TransactionStatus.APPROVED)
                        quantity += t.getQuantity();
                    break;
            }
        }
        return quantity;
    }
}
