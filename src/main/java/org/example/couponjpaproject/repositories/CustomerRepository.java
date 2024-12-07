package org.example.couponjpaproject.repositories;

import org.example.couponjpaproject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    @Query(value = "SELECT COUNT(*)>0 FROM jpacoupons.customers_vs_coupons as cvc WHERE coupons_id = ?1 AND customers_id = ?2", nativeQuery = true)
    int doCustomerOwn(int couponId, int customerId);

    @Query(value = "SELECT id FROM jpacoupons.customers WHERE email = ?1 AND password = ?2", nativeQuery = true)
    int getCustomerId(String email, String password);


}
