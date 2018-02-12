package com.cjimgarten.customer.model;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete

/**
 * CustomerRepository.java
 * Created by chris on 1/23/18.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}