package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.services.AdminServices;
import org.example.couponjpaproject.services.exceptions.CompanyAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.example.couponjpaproject.services.exceptions.CustomerAlreadyExistsException;
import org.example.couponjpaproject.services.exceptions.CustomerMayNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController{

    AdminServices service;

    public AdminController(AdminServices service) {
        this.service = service;
    }

    @PostMapping("/company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws CompanyAlreadyExistsException {
        service.addCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PutMapping("/company")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) throws CompanyMayNotExistException {
      service.updateCompany(company);
      return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @DeleteMapping("/company/{companyId}")
    public void deleteCompany(@PathVariable int companyId) throws CompanyMayNotExistException {
        service.deleteCompany(companyId);
    }

    @GetMapping("/allcompanies")
    public List<Company> getAllCompanies() {
        return service.getAllCompanies();
    }

    @GetMapping("/company/{companyId}")
    public Company getOneCompany(@PathVariable int companyId) throws CompanyMayNotExistException {
        return service.getOneCompany(companyId);
    }

    @PostMapping("/customer")
    public void addCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        service.addCustomer(customer);
    }

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerMayNotExistException {
        service.updateCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(customer);

    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable int customerId) {
        service.deleteCustomer(customerId);
    }

    @GetMapping("/allcustomers")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/customer/{customerId}")
    public Customer getOneCustomer(@PathVariable int customerId) throws CustomerMayNotExistException {
        return service.getOneCustomer(customerId);
    }


}
