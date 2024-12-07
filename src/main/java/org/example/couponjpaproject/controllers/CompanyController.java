package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.services.CompanyServices;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.CouponMayAlreadyExistException;
import org.example.couponjpaproject.services.exceptions.CouponMayNotExistException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/company/")
public class CompanyController {

    CompanyServices service;

    public CompanyController(CompanyServices service) {
        this.service = service;
    }

//    @Override
//    @PostMapping("/login")
//    public boolean login(String email, String password) {return false;}

    @PostMapping("/add")
    public void addCoupon(@RequestBody Coupon coupon) throws CouponMayAlreadyExistException, CouponIsExpiredException {
        service.addCoupon(coupon);
    }

    @PatchMapping()
    public void updateCoupon(@RequestBody Coupon coupon) throws CouponMayNotExistException {
        service.updateCoupon(coupon);
    }

    @DeleteMapping()
    public void deleteCoupon(int couponId) throws CouponMayNotExistException {
        service.deleteCoupon(couponId);
    }

    @GetMapping("all")
    public List<Coupon> getAll() {
        return service.getCompanyCoupons();
    }

    @GetMapping("/all/{category}")
    public List<Coupon> getAllByCategory(@PathVariable Category category) {
        return service.getCompanyCoupons(category);
    }

    @GetMapping("/all/max={maxPrice}")
    public List<Coupon> getAllByPrice(@PathVariable double maxPrice) {
        return service.getCompanyCoupons(maxPrice);
    }

    @GetMapping("details")
    public Company getDetails() {
        return service.getCompanyDetails();
    }


}
