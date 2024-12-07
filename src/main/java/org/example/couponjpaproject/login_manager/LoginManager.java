package org.example.couponjpaproject.login_manager;

import org.example.couponjpaproject.login_manager.loginExceptions.InvalidLoginException;
import org.example.couponjpaproject.services.ClientServices;
import org.example.couponjpaproject.services.CompanyServices;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.AdminServices;

import org.springframework.stereotype.Component;


@Component
public class LoginManager {

    private final AdminServices admin;
    private final CompanyServices company;
    private final CustomerServices customer;

    public LoginManager(AdminServices admin, CompanyServices company, CustomerServices customer) {
        this.admin = admin;
        this.company = company;
        this.customer = customer;
    }
    public ClientServices login(String email, String password, ClientType clientType) {
        try {
            switch (clientType) {
                case Administrator -> {
                    if (admin.login(email, password)) {
                        return admin;
                    } else {
                        throw new InvalidLoginException("Invalid credentials for Administrator.");
                    }
                }
                case Company -> {
                    if (company.login(email, password)) {
                        return company;
                    } else {
                        throw new InvalidLoginException("Invalid credentials for Company.");
                    }
                }
                case Customer -> {
                    if (customer.login(email, password)) {
                        return customer;
                    } else {
                        throw new InvalidLoginException("Invalid credentials for Customer.");
                    }
                }
                default -> throw new InvalidLoginException("Unknown client type.");
            }
        } catch (Exception e) {
            throw new InvalidLoginException("Login failed: " + e.getMessage());
        }
    }
}
