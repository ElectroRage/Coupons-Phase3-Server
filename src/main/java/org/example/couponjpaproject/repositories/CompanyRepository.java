package org.example.couponjpaproject.repositories;

import org.example.couponjpaproject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByNameOrEmail(String name, String email);

    boolean existsByNameAndEmail(String name, String email);

    boolean existsByEmailAndPassword(String email, String password);

    @Query(value = "SELECT id FROM jpacoupons.companies WHERE email = ?1 AND password = ?2", nativeQuery = true)
    int getCompanyId(String email, String passwrod);

}


