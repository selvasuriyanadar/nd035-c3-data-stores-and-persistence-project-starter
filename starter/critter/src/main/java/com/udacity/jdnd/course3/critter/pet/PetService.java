package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public PetModel savePet(@Valid PetModel model) {
        if (!customerRepository.existsById(model.getOwner().getId())) {
            throw new IllegalArgumentException("Customer Not Found.");
        }
        model.setOwner(entityManager.getReference(CustomerModel.class, model.getOwner().getId()));
        return petRepository.save(model);
    }

    @Transactional
    public void deletePet(PetModel model) {
        if (scheduleRepository.existsByPetIdAndGreaterThanOrEqualToDate(model.getId(), LocalDate.now())) {
            throw new IllegalStateException("The pet is scheduled for some activities.");
        }
        petRepository.delete(model);
    }

}
