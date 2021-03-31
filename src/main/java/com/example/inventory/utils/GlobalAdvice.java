package com.example.inventory.utils;

import com.example.inventory.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvice.class);

    @ExceptionHandler({NotEnoughQuantityException.class, FirmNotFoundException.class,
            ItemNotFoundException.class, SupplierNotFoundException.class,
            TransactionNotFoundException.class, UserNotFoundException.class,
            UserRoleNotFoundException.class, WarehouseNotFoundException.class,
            UserRoleNoPermissionsException.class})
    public ModelAndView handleHelloException(Exception exception) {
        LOGGER.error("Exception caught", exception);

        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("exceptionText", exception.getMessage());
        return modelAndView;
    }

}
