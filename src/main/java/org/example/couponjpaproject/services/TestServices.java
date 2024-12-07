package org.example.couponjpaproject.services;

import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.repositories.CompanyRepository;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.example.couponjpaproject.repositories.CustomerRepository;
import org.example.couponjpaproject.services.exceptions.CompanyMayNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

// This class will assist in the testing of the program,
// method created for testing it will be here.
public class TestServices {

    private final AdminServices adminServices;
    CouponRepository cupRep;
    CustomerRepository cusRep;
    CompanyRepository comRep;
    CustomerServices cusSer;
    CompanyServices comSer;

    public TestServices(CouponRepository cupRep, CustomerRepository cusRep, CompanyRepository comRep, CustomerServices cusSer, CompanyServices comSer, AdminServices adminServices) {
        this.cupRep = cupRep;
        this.cusRep = cusRep;
        this.comRep = comRep;
        this.cusSer = cusSer;
        this.comSer = comSer;
        this.adminServices = adminServices;
    }


    public void purchaseExpiredCoupon(int customerId, int couponId) {
        Coupon coupon = cupRep.findById(couponId).orElseThrow();
        Customer customer = cusRep.findById(customerId).orElseThrow();
        coupon.purchase();

        cupRep.save(coupon);
        customer.getCoupons().add(coupon);
        cusRep.save(customer);
    }

    public Coupon getOneCoupon(int couponId) {
        return cupRep.findById(couponId).orElseThrow();
    }

    public Company getOneCompany(int companyId) {
        return comRep.findById(companyId).orElseThrow();
    }

    public void dropSchema() {
        cupRep.dropSchema();
    }

    public CouponRepository getCupRep() {
        return cupRep;
    }

    public CustomerRepository getCusRep() {
        return cusRep;
    }

    public CompanyRepository getComRep() {
        return comRep;
    }

    //this method will be used to wipe the entire DB while also testing the delete
    // methods in the program.
    public void deleteDb() throws CompanyMayNotExistException {

        List<Company> companies = comRep.findAll();
        List<Customer> customers = cusRep.findAll();

        for (Customer c : customers) {
            adminServices.deleteCustomer(c.getId());
        }
        for (Company c : companies) {
            adminServices.deleteCompany(c.getId());
        }


    }

}
