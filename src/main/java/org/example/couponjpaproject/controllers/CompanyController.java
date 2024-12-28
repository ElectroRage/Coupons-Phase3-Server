package org.example.couponjpaproject.controllers;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.services.CompanyServices;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.CouponMayAlreadyExistException;
import org.example.couponjpaproject.services.exceptions.CouponMayNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("company")
public class CompanyController {

    CompanyServices service;

    public CompanyController(CompanyServices service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) throws CouponMayAlreadyExistException, CouponIsExpiredException {
        service.addCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(coupon);

    }

    @PutMapping("/update")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) throws CouponMayNotExistException, CouponIsExpiredException {
        service.updateCoupon(coupon);
        return ResponseEntity.status(HttpStatus.OK).body(coupon);
    }

    @DeleteMapping("/{couponId}")
    public boolean deleteCoupon(@PathVariable int couponId) throws CouponMayNotExistException {
        return service.deleteCoupon(couponId);
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
        System.out.println(service.toString());
        return service.getCompanyDetails();
    }


}
