package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }

    @Transactional
    public EmployeeModel saveEmployee(EmployeeModel employeeModel) {
        return employeeRepository.save(employeeModel);
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> daysAvailable, EmployeeModel model) {
        model.setDaysAvailable(daysAvailable);
        employeeRepository.save(model);
    }

}
