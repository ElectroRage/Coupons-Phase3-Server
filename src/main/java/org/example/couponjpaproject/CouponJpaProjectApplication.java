package org.example.couponjpaproject;

import org.example.couponjpaproject.beans.User;
import org.example.couponjpaproject.job.CouponExpirationDailyJob;
import org.example.couponjpaproject.tokenManager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class CouponJpaProjectApplication {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CouponJpaProjectApplication.class, args);

        //initiate coupon deletion job.
        CouponExpirationDailyJob couponJob = context.getBean(CouponExpirationDailyJob.class);
        Thread couponJobThread = new Thread(couponJob);
        couponJobThread.start();

    }

    @Bean
    public ConcurrentHashMap<String, User> activeTokens() {
        return new ConcurrentHashMap<>();
    }

}




