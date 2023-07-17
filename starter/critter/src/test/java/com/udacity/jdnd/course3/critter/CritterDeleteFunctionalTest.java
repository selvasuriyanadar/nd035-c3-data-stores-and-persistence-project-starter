package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.PetController;

import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional requirements for delete functionality
 */
@Transactional
public class CritterDeleteFunctionalTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PetController petController;

    /**
     * A pet can only be deleted if no schedule for it exists in the present day or any other future days
     */
    @Test
    public void testDeletePet() {
    }

}
