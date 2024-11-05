package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.services.AdminServices;
import org.example.couponjpaproject.services.exceptions.CompanyAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.example.couponjpaproject.services.exceptions.CustomerAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CustomerMayNotExistException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController extends ClientController {

    AdminServices service;

    public AdminController(AdminServices service) {
        this.service = service;
    }

    @Override
    @PostMapping("/login")
    public boolean login(@RequestParam String email, String password) {
        return false;
    }

    @PostMapping("/company")
    public void addCompany(@RequestBody Company company) throws CompanyAlreadyExistsException {
        service.addCompany(company);
    }

    @PatchMapping("/company")
    public void updateCompany(@RequestBody Company company) throws CompanyMayNotExistException {
        service.updateCompany(company);
    }

    @DeleteMapping("/company")
    public void deleteCompany(int companyId) throws CompanyMayNotExistException {
        service.deleteCompany(companyId);
    }

    @GetMapping("/allcompanies")
    public List<Company> getAllCompanies(){
        return  service.getAllCompanies();
    }

    @GetMapping("/company")
    public Company getOneCompany(int companyId) throws CompanyMayNotExistException {
        return service.getOneCompany(companyId);
    }

    @PostMapping("/customer")
    public void addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        service.addCustomer(customer);
    }

    @PatchMapping("/customer")
    public void updateCustomer(@RequestBody Customer customer) throws CustomerMayNotExistException {
        service.updateCustomer(customer);
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(int customerId){
        service.deleteCustomer(customerId);
    }

    @GetMapping("/allcustomers")
    public List<Customer> getAllCustomers(){
        return service.getAllCustomers();
    }

    @GetMapping("customer")
    public Customer getOneCustomer(int customerId) throws CustomerMayNotExistException {
        return service.getOneCustomer(customerId);
    }



}
