package org.example.couponjpaproject.services;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Coupon;
import org.example.couponjpaproject.beans.Customer;
import org.example.couponjpaproject.repositories.CouponRepository;
import org.example.couponjpaproject.repositories.CustomerRepository;
import org.example.couponjpaproject.services.exceptions.CouponIsExpiredException;
import org.example.couponjpaproject.services.exceptions.OwnedCouponException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Scope("prototype")
public class CustomerServices implements ClientServices {

    private int customerId;
    private CustomerRepository cusRep;
    private CouponRepository cupRep;

    public CustomerServices(CouponRepository couponRepository, CustomerRepository customerRepository) {
        this.cusRep = customerRepository;
        this.cupRep = couponRepository;
        this.customerId = 0;
    }

    public boolean login(String email, String password) {
        if (cusRep.existsByEmailAndPassword(email, password)) {
            customerId = cusRep.getCustomerId(email, password);
            return true;
        }
        return false;
    }

    public void purchaseCoupon(Coupon coupon) throws CouponIsExpiredException, OwnedCouponException {
        Date curDate = new Date(System.currentTimeMillis());
        if (coupon.getEndDate().before(curDate)) {
            throw new CouponIsExpiredException("Cannot Purchase Expired Coupon.");

        }
        if (cusRep.doCustomerOwn(coupon.getId(), customerId) > 0) {
            throw new OwnedCouponException("This Customer Already owns this coupon.");
        }
        Customer customer = getCustomerDetails();
        //reduces the amount by 1
        coupon.purchase();
        cupRep.save(coupon);
        customer.getCoupons().add(coupon);
        cusRep.save(customer);

    }

    public Set<Coupon> getCustomerCoupons() {
        return getCustomerDetails().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        return cupRep.findByCustomerIdAndCategory(customerId, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) {
        return cupRep.findByCustomerIdAndPrice(customerId, maxPrice);
    }

    public Customer getCustomerDetails() {
        return cusRep.findById(customerId).orElseThrow();
    }

}
