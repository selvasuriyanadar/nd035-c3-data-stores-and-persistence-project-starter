package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private CustomerModel complete(CustomerModel customerModel) {
        customerModel.setPetIds(customerRepository.fetchPetIdByCustomerId(customerModel.getId()));
        return customerModel;
    }

    @JsonView(CustomerViews.Public.class)
    @PostMapping("/customer")
    public CustomerModel createCustomer(@RequestBody CustomerModel customerModel){
        return complete(userService.saveCustomer(customerModel));
    }

    @JsonView(CustomerViews.Public.class)
    @PutMapping("/customer/{customerId}")
    public CustomerModel editCustomer(@PathVariable("customerId") CustomerModel customerModel, @RequestBody CustomerModel request){
        return complete(userService.saveCustomer(BeanUtil.transferIfNotNull(request, customerModel)));
    }

    @JsonView(CustomerViews.Public.class)
    @GetMapping("/customer/{customerId}")
    public CustomerModel getCustomer(@PathVariable("customerId") CustomerModel customerModel){
        return complete(customerModel);
    }

    @JsonView(CustomerViews.Public.class)
    @GetMapping("/customer")
    public List<CustomerModel> getAllCustomers(){
        return customerRepository.findAll().stream().map(customerModel -> complete(customerModel)).toList();
    }

    @JsonView(CustomerViews.Public.class)
    @GetMapping("/customer/pet/{petId}")
    public CustomerModel getOwnerByPet(@PathVariable long petId){
        Optional<CustomerModel> customerModelOpt = customerRepository.fetchByPetId(petId);
        if (customerModelOpt.isEmpty()) {
            throw new IllegalStateException("Could not find the owner of the pet.");
        }
        return complete(customerModelOpt.get());
    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") CustomerModel customerModel) {
        userService.deleteCustomer(customerModel);
    }

    @JsonView(EmployeeViews.Public.class)
    @PostMapping("/employee")
    public EmployeeModel createEmployee(@RequestBody EmployeeModel employeeModel) {
        return userService.saveEmployee(employeeModel);
    }

    @JsonView(EmployeeViews.Public.class)
    @PutMapping("/employee/{employeeId}")
    public EmployeeModel editEmployee(@PathVariable("employeeId") EmployeeModel employeeModel, @RequestBody EmployeeModel request){
        return userService.saveEmployee(BeanUtil.transferIfNotNull(request, employeeModel));
    }

    @PutMapping("/employee/availability/{employeeId}")
    public void updateAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable("employeeId") EmployeeModel employeeModel) {
        employeeModel.setDaysAvailable(daysAvailable);
        userService.saveEmployee(employeeModel);
    }

    @JsonView(EmployeeViews.Public.class)
    @GetMapping("/employee/{employeeId}")
    public EmployeeModel getEmployee(@PathVariable("employeeId") EmployeeModel employeeModel) {
        return employeeModel;
    }

    @JsonView(EmployeeViews.Public.class)
    @PostMapping("/employee/availability")
    public List<EmployeeModel> findEmployeesForService(@RequestBody @Valid EmployeeRequestDTO employeeDTO) {
        return employeeRepository.fetchBySkillsAndAvailableDay(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
    }

    @JsonView(EmployeeViews.Public.class)
    @GetMapping("/employee")
    public List<EmployeeModel> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") EmployeeModel employeeModel) {
        userService.deleteEmployee(employeeModel);
    }
}
