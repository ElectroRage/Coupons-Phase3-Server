package org.example.couponjpaproject.services;

import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebServices {

    CouponRepository couponRepository;

    public WebServices(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    //new method for getting all coupons to show in the main page
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
