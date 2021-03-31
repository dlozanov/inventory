package com.example.inventory.service.impl;

import com.example.inventory.exception.ItemNotFoundException;
import com.example.inventory.exception.NotEnoughQuantityException;
import com.example.inventory.exception.TransactionNotFoundException;
import com.example.inventory.exception.UserNotFoundException;
import com.example.inventory.model.entity.ItemEntity;
import com.example.inventory.model.entity.TransactionEntity;
import com.example.inventory.model.entity.UserEntity;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;
import com.example.inventory.model.service.TransactionServiceModel;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.repository.TransactionRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.service.ItemService;
import com.example.inventory.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final ItemRepository itemRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(ItemRepository itemRepository, TransactionRepository transactionRepository, UserRepository userRepository, ItemService itemService, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionServiceModel findById(long id) {
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() ->
                new TransactionNotFoundException("Транзакцията не може да бъде открита."));
        return modelMapper.map(transactionEntity, TransactionServiceModel.class);
    }

    @Override
    public void savePendingTransaction(long itemId, double quantity, String address, String username) {

        ItemEntity item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(
                "Артикула не може да бъде намерен."
        ));
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(TransactionType.SELL);
        transactionEntity.setTransactionStatus(TransactionStatus.PENDING);
        transactionEntity.setBuyer(user);
        transactionEntity.setFirm(item.getFirm());
        transactionEntity.setDateTime(LocalDateTime.now());
        transactionEntity.setItem(item);
        transactionEntity.setPrice(item.getOutgoingPrice());
        transactionEntity.setQuantity(quantity);
        transactionEntity.setSum(item.getOutgoingPrice().multiply(BigDecimal.valueOf(quantity)));
        transactionEntity.setAddress(address);

        transactionRepository.save(transactionEntity);
    }

    @Override
    public void confirmPendingTransaction(long id, String username) {

        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() ->
                new TransactionNotFoundException("Транзакцията не може да бъде открита."));

        if(!itemService.hasEnoughQuantity(transactionEntity.getItem().getId(), transactionEntity.getQuantity()))
            throw new NotEnoughQuantityException("Няма достатъчно количество от артикул: " + transactionEntity.getItem().getName());

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        transactionEntity.setUser(user);
        transactionEntity.setTransactionStatus(TransactionStatus.APPROVED);

        transactionRepository.save(transactionEntity);
    }

    @Override
    public void declinePendingTransaction(long id, String name) {
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElseThrow(() ->
                new TransactionNotFoundException("Транзакцията не може да бъде открита."));

        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        transactionEntity.setUser(user);
        transactionEntity.setTransactionStatus(TransactionStatus.DECLINED);

        transactionRepository.save(transactionEntity);
    }

    @Override
    public void sell(long id, double quantity, String name) {
        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(
                "Артикула не може да бъде намерен."
        ));
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(TransactionType.SELL);
        transactionEntity.setTransactionStatus(TransactionStatus.APPROVED);
        transactionEntity.setUser(user);
        transactionEntity.setFirm(item.getFirm());
        transactionEntity.setDateTime(LocalDateTime.now());
        transactionEntity.setItem(item);
        transactionEntity.setPrice(item.getOutgoingPrice());
        transactionEntity.setQuantity(quantity);
        transactionEntity.setSum(item.getOutgoingPrice().multiply(BigDecimal.valueOf(quantity)));

        transactionRepository.save(transactionEntity);
    }

    @Override
    public void purchase(long id, double quantity, String name) {
        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(
                "Артикула не може да бъде намерен."
        ));
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionType(TransactionType.PURCHASE);
        transactionEntity.setTransactionStatus(TransactionStatus.APPROVED);
        transactionEntity.setUser(user);
        transactionEntity.setFirm(item.getFirm());
        transactionEntity.setDateTime(LocalDateTime.now());
        transactionEntity.setItem(item);
        transactionEntity.setPrice(item.getIncomingPrice());
        transactionEntity.setQuantity(quantity);
        transactionEntity.setSum(item.getIncomingPrice().multiply(BigDecimal.valueOf(quantity)));

        transactionRepository.save(transactionEntity);
    }

    @Override
    public void voidSell(long id, String name) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Транзакцията не може да бъде намерена"));
        if(transactionEntity.getTransactionType() != TransactionType.SELL
            || transactionEntity.getTransactionStatus() != TransactionStatus.APPROVED)
            throw new TransactionNotFoundException("Транзакцията не може да бъде намерена");
        transactionEntity.setTransactionStatus(TransactionStatus.VOID);
        transactionRepository.save(transactionEntity);
        transactionEntity.setId(0);
        transactionEntity.setTransactionType(TransactionType.VOID_SELL);
        transactionEntity.setTransactionStatus(TransactionStatus.APPROVED);
        transactionEntity.setUser(user);
        transactionEntity.setDateTime(LocalDateTime.now());
        transactionRepository.save(transactionEntity);
    }

    @Override
    public void voidPurchase(long id, String name) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(
                "Потребителят не може да бъде намерен."
        ));
        TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Транзакцията не може да бъде намерена"));
        if(transactionEntity.getTransactionType() != TransactionType.PURCHASE
            || transactionEntity.getTransactionStatus() != TransactionStatus.APPROVED)
            throw new TransactionNotFoundException("Транзакцията не може да бъде намерена");
        transactionEntity.setTransactionStatus(TransactionStatus.VOID);
        transactionRepository.save(transactionEntity);
        transactionEntity.setId(0);
        transactionEntity.setTransactionType(TransactionType.VOID_PURCHASE);
        transactionEntity.setTransactionStatus(TransactionStatus.APPROVED);
        transactionEntity.setUser(user);
        transactionEntity.setDateTime(LocalDateTime.now());
        transactionRepository.save(transactionEntity);
    }
}
