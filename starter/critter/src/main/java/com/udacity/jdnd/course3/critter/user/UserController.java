package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerModel model = BeanUtil.transfer(customerDTO, new CustomerModel());
        if (customerDTO.getPetIds() != null) {
            model.setPets(customerDTO.getPetIds().stream().distinct().map(petId -> entityManager.getReference(PetModel.class, petId)).toList());
        }
        return BeanUtil.transfer(userService.saveCustomer(model), new CustomerDTO());
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream().map(model -> BeanUtil.transfer(model, new CustomerDTO())).toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Optional<CustomerModel> modelOpt = customerRepository.fetchByPetId(petId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Could not find the owner of the pet.");
        }
        return BeanUtil.transfer(modelOpt.get(), new CustomerDTO());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return BeanUtil.transfer(userService.saveEmployee(BeanUtil.transfer(employeeDTO, new EmployeeModel())), new EmployeeDTO());
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<EmployeeModel> modelOpt = employeeRepository.findById(employeeId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Could not find Employee.");
        }
        return BeanUtil.transfer(modelOpt.get(), new EmployeeDTO());
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Optional<EmployeeModel> modelOpt = employeeRepository.findById(employeeId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Could not find Employee.");
        }
        userService.setAvailability(daysAvailable, modelOpt.get());
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeRepository.fetchBySkillsAndAvailableDay(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek()).stream().map(model -> BeanUtil.transfer(model, new EmployeeDTO())).toList();
    }

}
