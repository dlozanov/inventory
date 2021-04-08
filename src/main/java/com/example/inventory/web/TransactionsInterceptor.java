package com.example.inventory.web;

import com.example.inventory.model.service.TransactionServiceModel;
import com.example.inventory.service.LogService;
import com.example.inventory.service.TransactionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class TransactionsInterceptor implements HandlerInterceptor {

    private final TransactionService transactionService;
    private final LogService logService;

    public TransactionsInterceptor(TransactionService transactionService, LogService logService) {
        this.transactionService = transactionService;
        this.logService = logService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(request.getRequestURI().contains("transactions/buy")
                && request.getMethod().equalsIgnoreCase("POST")){
            int index = request.getRequestURI().lastIndexOf('/') + 1;
            int id = Integer.parseInt(request.getRequestURI().substring(index));
            if(id >= 1) {
                TransactionServiceModel transactionServiceModel = transactionService
                        .findById(id);

                logService.save(
                        String.format("Transaction with item: %s with quantity %.2f and sum" +
                                " %.2f is purchased by user %s at %s.", transactionServiceModel.getItem().getName(),
                                transactionServiceModel.getQuantity(), transactionServiceModel.getSum(),
                                request.getUserPrincipal().getName(),
                                LocalDateTime.now().toString())
                );
            }
        }
        return true;
    }

}
