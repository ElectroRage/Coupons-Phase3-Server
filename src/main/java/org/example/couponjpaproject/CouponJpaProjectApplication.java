package org.example.couponjpaproject;

import org.example.couponjpaproject.tokenManager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class CouponJpaProjectApplication {

//    private final TokenManager tokenManager;
//
//    public CouponJpaProjectApplication(TokenManager tokenManager) {
//        this.tokenManager = tokenManager;
//    }


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CouponJpaProjectApplication.class, args);
    }

    @Bean
    public ConcurrentHashMap<String,String> activeTokens() {
        return new ConcurrentHashMap<>();
    }

}




