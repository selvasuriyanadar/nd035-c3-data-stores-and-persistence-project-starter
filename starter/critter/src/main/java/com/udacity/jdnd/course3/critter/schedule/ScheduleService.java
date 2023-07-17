package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.pet.PetRepository;

import jakarta.validation.Valid;
import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public ScheduleModel createSchedule(@Valid ScheduleModel scheduleModel) {
        scheduleModel.setEmployees(scheduleModel.getEmployees().stream().filter(BeanUtil.distinctByKey(EmployeeModel::getId)).map(employee -> {
            if (!employeeRepository.existsById(employee.getId())) {
                throw new IllegalArgumentException("Employee Not Found.");
            }
            return entityManager.getReference(EmployeeModel.class, employee.getId());
        }).toList());
        scheduleModel.setPets(scheduleModel.getPets().stream().filter(BeanUtil.distinctByKey(PetModel::getId)).map(pet -> {
            if (!petRepository.existsById(pet.getId())) {
                throw new IllegalArgumentException("Pet Not Found.");
            }
            return entityManager.getReference(PetModel.class, pet.getId());
        }).toList());
        return scheduleRepository.save(scheduleModel);
    }

    @Transactional
    public void deleteSchedule(ScheduleModel scheduleModel) {
        scheduleRepository.delete(scheduleModel);
    }

}
