package org.example.couponjpaproject.repositories;

import org.example.couponjpaproject.beans.Category;
import org.example.couponjpaproject.beans.Company;
import org.example.couponjpaproject.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    void deleteCouponByCompany(Company company);

    List<Coupon> findCouponsByCompanyId(int companyId);

    List<Coupon> findCouponsByCompanyIdAndCategory(int companyId, Category category);

    List<Coupon> findCouponsByCompanyIdAndPriceLessThan(int companyId, double price);

    @Query(value = "SELECT * FROM coupons c join customers_vs_coupons cvc " +
            "ON c.id = cvc.coupons_id WHERE cvc.customers_id = ?1 AND c.Category = ?2",nativeQuery = true)
    List<Coupon> findByCustomerIdAndCategory(int customerId, Category category);

    @Query(value = "SELECT * FROM coupons c join customers_vs_coupons cvc " +
            "on c.id=cvc.coupons_id WHERE cvc.customers_id = ?1 AND c.price < ?2",nativeQuery = true)
    List<Coupon> findByCustomerIdAndPrice(int customerId, double maxPrice);


    Boolean existsCouponByTitleAndCompany(String title, Company company);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM jpacoupons.customers_vs_coupons WHERE coupons_id = ?1", nativeQuery = true)
    int deleteCouponAssociation(int couponId);

    // I saw no reason for creating a Test bean just to create a repository for its own so its here out
    //just for comfort.
    @Modifying
    @Transactional
    @Query(value = "DROP Schema jpacoupons",nativeQuery = true)
    void dropSchema();


}
