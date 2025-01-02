package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.services.CustomerServices;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.OwnedCouponException;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("customer")
public class CustomerController {


    private CustomerServices service;

    public CustomerController(CustomerServices service) {
        this.service = service;
    }

    @PostMapping("/purchase")
    public void purchaseCoupon(@RequestBody Coupon coupon) throws OwnedCouponException, CouponIsExpiredException {
        service.purchaseCoupon(coupon);
    }

    @GetMapping("all")
    public Set<Coupon> getCustomerCoupons() {
        return service.getCustomerCoupons();
    }

    @GetMapping("/all/category={category}")
    public List<Coupon> getCustomerCoupons(@PathVariable Category category) {
        return service.getCustomerCoupons(category);
    }

    @GetMapping("/all/maxprice={maxPrice}")
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        return service.getCustomerCoupons(maxPrice);
    }

    @GetMapping("details")
    public Customer details() {
        return service.getCustomerDetails();
    }


}
