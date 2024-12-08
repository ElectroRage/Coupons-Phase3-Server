package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.OwnedCouponException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class CustomerController   {

    CustomerServices service;

    //Class needs to be tested

    //TODO: apparantely this needs to be tested, additionally I need to check the error handling

    public CustomerController(CustomerServices service) {
        this.service = service;
    }


    @PostMapping("/purchase")
    public void purchaseCoupon(@RequestBody Coupon coupon) throws OwnedCouponException, CouponIsExpiredException {
        service.purchaseCoupon(coupon);
    }

    @GetMapping("getcoupons1")
    public Set<Coupon> getCustomerCoupons() {
        return service.getCustomerCoupons();
    }

    @GetMapping("getcoupons2")
    public List<Coupon> getCustomerCoupons(Category category) {
        return service.getCustomerCoupons(category);
    }


    @GetMapping("getcoupons3")
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        return service.getCustomerCoupons(maxPrice);
    }

    @GetMapping("customer1")
    public Customer details() {
        return service.getCustomerDetails();
    }

}
