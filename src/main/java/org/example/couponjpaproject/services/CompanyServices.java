package org.example.couponjpaproject.services;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.repositories.CompanyRepository;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.CouponMayAlreadyExistException;
import org.example.couponjpaproject.services.exceptions.CouponMayNotExistException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyServices implements ClientServices {

    private int companyId;
    private CouponRepository cupRep;
    private CompanyRepository comRep;


    public CompanyServices(CouponRepository cupRep, CompanyRepository comRep) {
        this.cupRep = cupRep;
        this.comRep = comRep;
        this.companyId = 0;

    }

    public boolean login(String email, String password) {
        if (comRep.existsByEmailAndPassword(email, password)) {
            companyId = comRep.getCompanyId(email, password);
            return true;
        }
        return false;
    }

    public void addCoupon(Coupon coupon) throws CouponMayAlreadyExistException, CouponIsExpiredException {
        Date curDate = new Date(System.currentTimeMillis());
        if (coupon.getEndDate().before(curDate)) {
            throw new CouponIsExpiredException("Cannot Add Expired Coupon.");

        }
        if (cupRep.existsCouponByTitleAndCompany(coupon.getTitle(), coupon.getCompany())) {
            throw new CouponMayAlreadyExistException("This Coupon May Already Exist, Check Credentials.");
        }
        cupRep.save(coupon);
    }

    public void updateCoupon(Coupon coupon) throws CouponMayNotExistException {
        if (cupRep.existsById(coupon.getId())) {
            cupRep.save(coupon);
        } else {
            throw new CouponMayNotExistException("The Coupon may not exist.");
        }
    }

    public boolean deleteCoupon(int couponId) throws CouponMayNotExistException {
        if (cupRep.existsById(couponId)) {
            cupRep.deleteCouponAssociation(couponId);
            cupRep.deleteById(couponId);
            return true;
        } else
            throw new CouponMayNotExistException("Coupon May Not Exist,Check Credentials.");
    }

    public List<Coupon> getCompanyCoupons() {
        return cupRep.findCouponsByCompanyId(companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category) {
        return cupRep.findCouponsByCompanyIdAndCategory(companyId, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return cupRep.findCouponsByCompanyIdAndPriceLessThan(companyId, maxPrice);
    }

    public Company getCompanyDetails() {
        return comRep.findById(companyId).orElseThrow();
    }


}
