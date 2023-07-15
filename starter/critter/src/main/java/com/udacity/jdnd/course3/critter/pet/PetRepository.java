package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@Repository
public interface PetRepository extends JpaRepository<PetModel, Long> {

    @Query("select a from PetModel a join a.owner o where o.id = ?1")
    public List<PetModel> fetchByOwnerId(long ownerId);

}
