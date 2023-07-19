package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

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

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerModel model = BeanUtil.transferWithIgnoreFields(customerDTO, new CustomerModel(), "id", "petIds");
        CustomerDTO customerDTOResponse = BeanUtil.transfer(userService.saveCustomer(model), new CustomerDTO());
        if (model.getPets() != null) {
            customerDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
        }
        return customerDTOResponse;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream().map(model -> {
            CustomerDTO customerDTOResponse = BeanUtil.transfer(model, new CustomerDTO());
            if (model.getPets() != null) {
                customerDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
            }
            return customerDTOResponse;
        }).toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Optional<CustomerModel> modelOpt = customerRepository.fetchByPetId(petId);
        if (modelOpt.isEmpty()) {
            throw new IllegalStateException("Could not find the owner of the pet.");
        }
        CustomerDTO customerDTOResponse = BeanUtil.transfer(modelOpt.get(), new CustomerDTO());
        if (modelOpt.get().getPets() != null) {
            customerDTOResponse.setPetIds(modelOpt.get().getPets().stream().map(pet -> pet.getId()).toList());
        }
        return customerDTOResponse;
    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable long customerId) {
        Optional<CustomerModel> modelOpt = customerRepository.findById(customerId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer Not Found.");
        }
        userService.deleteCustomer(modelOpt.get());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return BeanUtil.transfer(userService.saveEmployee(BeanUtil.transferWithIgnoreFields(employeeDTO, new EmployeeModel(), "id")), new EmployeeDTO());
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Optional<EmployeeModel> modelOpt = employeeRepository.findById(employeeId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Could not find Employee.");
        }
        modelOpt.get().setDaysAvailable(daysAvailable);
        userService.saveEmployee(modelOpt.get());
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<EmployeeModel> modelOpt = employeeRepository.findById(employeeId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Could not find Employee.");
        }
        return BeanUtil.transfer(modelOpt.get(), new EmployeeDTO());
    }

    @PostMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeRepository.fetchBySkillsAndAvailableDay(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek()).stream().map(model -> BeanUtil.transfer(model, new EmployeeDTO())).toList();
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll().stream().map(model -> {
            return BeanUtil.transfer(model, new EmployeeDTO());
        }).toList();
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId) {
        Optional<EmployeeModel> modelOpt = employeeRepository.findById(employeeId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Employee Not Found.");
        }
        userService.deleteEmployee(modelOpt.get());
    }
}
