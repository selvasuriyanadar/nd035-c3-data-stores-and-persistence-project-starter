package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public PetModel savePet(PetModel model) {
        if (!customerRepository.existsById(model.getOwner().getId())) {
            throw new IllegalArgumentException("Customer Not Found.");
        }
        model.setOwner(entityManager.getReference(CustomerModel.class, model.getOwner().getId()));
        return petRepository.save(model);
    }

}
