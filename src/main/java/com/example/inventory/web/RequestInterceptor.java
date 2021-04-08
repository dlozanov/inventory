package com.example.inventory.web;

import com.example.inventory.model.service.TransactionServiceModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String user = request.getUserPrincipal() == null ? "anonymous" : request.getUserPrincipal().getName();
        System.out.println(String.format("Request to: %s from user: %s at: %s",
                request.getRequestURI(), user, LocalDateTime.now().toString()));
        return true;
    }

}
