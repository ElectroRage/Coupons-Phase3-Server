package org.example.couponjpaproject.services;

import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.repositories.CompanyRepository;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.example.couponjpaproject.repositories.CustomerRepository;
import org.example.couponjpaproject.services.exceptions.CompanyAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.example.couponjpaproject.services.exceptions.CustomerAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CustomerMayNotExistException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices implements ClientServices {

    CompanyRepository comRep;
    CustomerRepository cusRep;
    CouponRepository cupRep;

    public AdminServices(CompanyRepository comRep, CustomerRepository cusRep, CouponRepository cupRep) {
        this.comRep = comRep;
        this.cusRep = cusRep;
        this.cupRep = cupRep;
    }

    public boolean login(String email, String password) {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        }
        return false;
    }

    public void addCompany(Company company) throws CompanyAlreadyExistsException {
        if (comRep.existsByNameOrEmail(company.getName(), company.getEmail())) {
            throw new CompanyAlreadyExistsException("Company May Already Exist, Check Name And Email.");
        } else {
            comRep.save(company);
        }

    }

    public void updateCompany(Company company) throws CompanyMayNotExistException {
        if (comRep.existsById(company.getId())) {
            if (comRep.findById(company.getId()).orElseThrow().equals(company)) {
                comRep.save(company);
            }
        } else
            throw new CompanyMayNotExistException("Check Credentials, Company May Not Exist");
    }

    public void deleteCompany(int id) throws CompanyMayNotExistException {
        if (comRep.existsById(id)) {
            for (Coupon c : getOneCompany(id).getCoupons())
                cupRep.deleteCouponAssociation(c.getId());
            cupRep.deleteCouponByCompany(getOneCompany(id));
            comRep.deleteById(id);
        } else {
            throw new CompanyMayNotExistException("Check Credentials, Company May Not Exist");
        }
    }

    public List<Company> getAllCompanies() {
        return comRep.findAll();
    }

    public Company getOneCompany(int id) throws CompanyMayNotExistException {
        if (comRep.existsById(id)) {
            return comRep.findById(id).orElseThrow();
        } else {
            throw new CompanyMayNotExistException("Check Credentials, Company May Not Exist");
        }
    }

    public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (!cusRep.existsByEmail(customer.getEmail())) {
            cusRep.save(customer);
        } else {
            throw new CustomerAlreadyExistsException("This Customers Email Already Exist");
        }
    }

    public void updateCustomer(Customer customer) throws CustomerMayNotExistException {
        if (cusRep.existsById(customer.getId())) {
            cusRep.save(customer);
        } else {
            throw new CustomerMayNotExistException("Customer ID Could Not Be Found, Customer may not Exist.");
        }
    }

    public void deleteCustomer(int id) {
        if (cusRep.existsById(id)) {
            cusRep.deleteById(id);
        } else {
            System.out.println("Customer was null");
        }
    }

    public List<Customer> getAllCustomers() {
        return cusRep.findAll();
    }

    public Customer getOneCustomer(int id) throws CustomerMayNotExistException {
        if (cusRep.existsById(id)) {
            return cusRep.findById(id).orElseThrow();
        } else {
            throw new CustomerMayNotExistException("Customer ID Could Not Be Found, Customer may not Exist.");
        }
    }


}
