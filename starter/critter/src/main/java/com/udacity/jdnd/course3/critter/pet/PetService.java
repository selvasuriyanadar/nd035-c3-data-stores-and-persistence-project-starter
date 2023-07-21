package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleLogic;

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

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private ScheduleLogic scheduleLogic;

    @Transactional
    public PetModel savePet(@Valid PetModel model) {
        scheduleLogic.updatePet(model);
        return petRepository.save(model);
    }

    @Transactional
    public void deletePet(PetModel model) {
        scheduleLogic.removePet(model);
        petRepository.delete(model);
    }

    @Transactional
    public PetTypeModel configurePetType(@Valid PetTypeModel model) {
        scheduleLogic.updatePetType(model);
        return petTypeRepository.save(model);
    }

}
