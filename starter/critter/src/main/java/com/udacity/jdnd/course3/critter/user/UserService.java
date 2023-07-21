package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleLogic;

import jakarta.validation.Valid;
import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ScheduleLogic scheduleLogic;

    @Transactional
    public CustomerModel saveCustomer(@Valid CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }

    @Transactional
    public EmployeeModel saveEmployee(@Valid EmployeeModel employeeModel) {
        scheduleLogic.updateEmployee(employeeModel);
        return employeeRepository.save(employeeModel);
    }

    @Transactional
    public void deleteCustomer(CustomerModel model) {
        scheduleLogic.removeCustomer(model);
        customerRepository.delete(model);
    }

    @Transactional
    public void deleteEmployee(EmployeeModel model) {
        scheduleLogic.removeEmployee(model);
        employeeRepository.delete(model);
    }

}
