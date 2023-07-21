package com.udacity.jdnd.course3.critter.user;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import java.time.DayOfWeek;
import java.util.*;

public class UserTestController {

    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    public UserTestController(int port, TestRestTemplate testRestTemplate) {
        this.baseUrl = "http://localhost:" + port + "/user";
        this.testRestTemplate = testRestTemplate;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        return testRestTemplate.postForObject(baseUrl + "/customer", customerDTO, CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers(){
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/customer", CustomerDTO[].class)).toList();
    }

    public CustomerDTO getOwnerByPet(long petId){
        return testRestTemplate.getForObject(baseUrl + "/customer/pet/" + petId, CustomerDTO.class);
    }

    public void deleteCustomer(long customerId) {
        testRestTemplate.delete(baseUrl + "/customer/" + customerId);
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        return testRestTemplate.postForObject(baseUrl + "/employee", employeeDTO, EmployeeDTO.class);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        testRestTemplate.put(baseUrl + "/employee/availability/" + employeeId, daysAvailable);
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return testRestTemplate.getForObject(baseUrl + "/employee/" + employeeId, EmployeeDTO.class);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        return Arrays.stream(testRestTemplate.postForObject(baseUrl + "/employee/availability", employeeDTO, EmployeeDTO[].class)).toList();
    }

    public List<EmployeeDTO> getAllEmployees(){
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/employee", EmployeeDTO[].class)).toList();
    }

    public void deleteEmployee(long employeeId) {
        testRestTemplate.delete(baseUrl + "/employee/" + employeeId);
    }

}
