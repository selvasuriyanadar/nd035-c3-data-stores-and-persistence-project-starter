package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.pet.PetRepository;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
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

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        if (customerModel.getName() == null || customerModel.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Name, Phone Number are all required.");
        }
        if (customerModel.getPets() != null) {
            customerModel.setPets(customerModel.getPets().stream().distinct().map(pet -> {
                if (!petRepository.existsById(pet.getId())) {
                    throw new IllegalArgumentException("Pet Not Found.");
                }
                return entityManager.getReference(PetModel.class, pet.getId());
            }).toList());
        }
        return customerRepository.save(customerModel);
    }

    @Transactional
    public EmployeeModel saveEmployee(EmployeeModel employeeModel) {
        if (employeeModel.getName() == null) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (employeeModel.getSkills() == null || employeeModel.getSkills().isEmpty()) {
            throw new IllegalArgumentException("Skills are required.");
        }
        if (employeeModel.getDaysAvailable() == null || employeeModel.getDaysAvailable().isEmpty()) {
            throw new IllegalArgumentException("Days Available are required.");
        }
        return employeeRepository.save(employeeModel);
    }

    @Transactional
    public void setAvailability(Set<DayOfWeek> daysAvailable, EmployeeModel model) {
        model.setDaysAvailable(daysAvailable);
        saveEmployee(model);
    }

}
