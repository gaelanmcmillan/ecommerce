package com.gm.ecommerce.repository;
import com.gm.ecommerce.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
