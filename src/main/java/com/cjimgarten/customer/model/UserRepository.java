package com.cjimgarten.customer.model;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete

import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository.java
 * Created by chris on 2/12/18.
 */
public interface UserRepository extends CrudRepository<User, Long>{

}
