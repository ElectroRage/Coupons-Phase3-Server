package org.example.couponjpaproject;

import org.example.couponjpaproject.Job.CouponExpirationDailyJob;
import org.example.couponjpaproject.LoginManager.LoginManager;
import org.example.couponjpaproject.services.exceptions.CompanyAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.example.couponjpaproject.services.exceptions.CustomerAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CustomerMayNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponJpaProjectApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CouponJpaProjectApplication.class, args);
    }

}




