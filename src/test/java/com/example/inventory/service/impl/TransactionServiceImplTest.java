package com.example.inventory.service.impl;

import com.example.inventory.model.entity.TransactionEntity;
import com.example.inventory.model.entity.enums.TransactionStatus;
import com.example.inventory.model.entity.enums.TransactionType;
import com.example.inventory.model.service.TransactionServiceModel;
import com.example.inventory.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    private TransactionEntity transaction1, transaction2, transaction3;

    @Mock
    TransactionRepository mockTransactionRepository;

    TransactionServiceImpl serviceToTest;

    @BeforeEach
    public void init(){
        transaction1 = new TransactionEntity();
        transaction1.setTransactionType(TransactionType.SELL);
        transaction1.setTransactionStatus(TransactionStatus.APPROVED);
        transaction1.setDateTime(LocalDateTime.now());
        transaction1.setPrice(BigDecimal.valueOf(12.25));
        transaction1.setQuantity(100);
        transaction1.setSum(transaction1.getPrice().multiply(BigDecimal.valueOf(100)));
        transaction2 = new TransactionEntity();
        transaction2.setTransactionType(TransactionType.PURCHASE);
        transaction2.setTransactionStatus(TransactionStatus.APPROVED);
        transaction2.setDateTime(LocalDateTime.now());
        transaction2.setPrice(BigDecimal.valueOf(1.25));
        transaction2.setQuantity(10);
        transaction2.setSum(transaction2.getPrice().multiply(BigDecimal.valueOf(10)));
        transaction3 = new TransactionEntity();
        transaction3.setTransactionType(TransactionType.SELL);
        transaction3.setTransactionStatus(TransactionStatus.PENDING);
        transaction3.setDateTime(LocalDateTime.now());
        transaction3.setPrice(BigDecimal.valueOf(155.25));
        transaction3.setQuantity(5252);
        transaction3.setSum(transaction3.getPrice().multiply(BigDecimal.valueOf(5252)));

        serviceToTest = new TransactionServiceImpl(null, mockTransactionRepository, null,
                null, new ModelMapper());
    }

    @Test
    public void testTransactionServiceFindById(){
        Mockito.when(mockTransactionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(transaction1));

        TransactionServiceModel model = serviceToTest.findById(1);

        Assertions.assertEquals(model.getTransactionType(), transaction1.getTransactionType());
        Assertions.assertEquals(model.getTransactionStatus(), transaction1.getTransactionStatus());
        Assertions.assertEquals(model.getDateTime(), transaction1.getDateTime());
        Assertions.assertEquals(model.getAddress(), transaction1.getAddress());
        Assertions.assertEquals(model.getPrice(), transaction1.getPrice());
        Assertions.assertEquals(model.getQuantity(), transaction1.getQuantity());
        Assertions.assertEquals(model.getSum(), transaction1.getSum());
    }
}
