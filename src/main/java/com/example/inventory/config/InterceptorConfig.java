package com.example.inventory.config;

import com.example.inventory.web.RequestInterceptor;
import com.example.inventory.web.TransactionsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  private final TransactionsInterceptor transactionsInterceptor;
  private final RequestInterceptor requestInterceptor;

  public InterceptorConfig(TransactionsInterceptor transactionsInterceptor, RequestInterceptor requestInterceptor) {
    this.transactionsInterceptor = transactionsInterceptor;
    this.requestInterceptor = requestInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(transactionsInterceptor);
    registry.addInterceptor(requestInterceptor);
  }
}
