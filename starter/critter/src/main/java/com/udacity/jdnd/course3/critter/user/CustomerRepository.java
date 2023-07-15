package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    @Query("select o from PetModel a join a.owner o where a.id = ?1")
    public Optional<CustomerModel> fetchByPetId(long petId);

    /*@Query("select a from CustomerModel a join a.pets p where p.id = ?1")
    public Optional<CustomerModel> fetchByPetId(long petId);*/

}
