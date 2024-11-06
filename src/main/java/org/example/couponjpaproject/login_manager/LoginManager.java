package org.example.couponjpaproject.login_manager;

import org.example.couponjpaproject.services.ClientServices;
import org.example.couponjpaproject.services.CompanyServices;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.AdminServices;

import org.springframework.stereotype.Component;


@Component
public class LoginManager {

    private AdminServices admin;
    private CompanyServices company;
    private CustomerServices customer;

    public LoginManager(AdminServices admin, CompanyServices company, CustomerServices customer) {
        this.admin = admin;
        this.company = company;
        this.customer = customer;
    }

    public ClientServices login(String email, String password, ClientType clientType) {
        switch ((clientType)) {
            case Administrator -> {
                if (admin.login(email, password)) {
                    return admin;
                }
            }
            case Company -> {
                if (company.login(email, password)) {
                    return company;
                }
            }
            case Customer -> {
                if (customer.login(email, password)) {
                    return customer;
                }
            }
        }
        return null;
    }
}
