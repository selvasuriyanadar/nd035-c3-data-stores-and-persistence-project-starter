package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetTestController;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
import com.udacity.jdnd.course3.critter.schedule.ScheduleTestController;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional requirements for delete functionality
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CritterDeleteFunctionalTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EntityManager entityManager;

    private PetTestController petController;

    private UserTestController userController;

    private ScheduleTestController scheduleController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup() {
        this.userController = new UserTestController(port, testRestTemplate);
        this.petController = new PetTestController(port, testRestTemplate);
        this.scheduleController = new ScheduleTestController(port, testRestTemplate);
    }

    /**
     * A pet can only be deleted if no schedule for it exists in the present day or any other future days
     */
    @Test
    public void testDeletePet() {
        CustomerDTO customerDTO = CritterFunctionalTest.createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        PetDTO petDTO = CritterFunctionalTest.createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        // check if the pet disappears
        petController.deletePet(newPet.getId());
        List<PetDTO> retrievedPets = petController.getPets();
        Assertions.assertTrue(retrievedPets.size() == 0);
    }

    /**
     * A customer can only be deleted if no schedule for it exists in the present day or any other future days
     * If a customer is deleted then all their pets are also deleted
     */
    @Test
    public void testDeleteCustomer() {
        CustomerDTO customerDTO = CritterFunctionalTest.createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        PetDTO petDTO1 = CritterFunctionalTest.createPetDTO();
        petDTO1.setOwnerId(newCustomer.getId());
        PetDTO newPet1 = petController.savePet(petDTO1);
        PetDTO petDTO2 = CritterFunctionalTest.createPetDTO();
        petDTO2.setOwnerId(newCustomer.getId());
        PetDTO newPet2 = petController.savePet(petDTO2);

        // check if the customer disappears
        userController.deleteCustomer(newCustomer.getId());
        List<CustomerDTO> retrievedCustomers = userController.getAllCustomers();
        Assertions.assertTrue(retrievedCustomers.size() == 0);

        // check if the pets disappear
        List<PetDTO> retrievedPets = petController.getPets();
        Assertions.assertTrue(retrievedPets.size() == 0);
    }

    /**
     * A employee can only be deleted if no schedule for it exists in the present day or any other future days
     */
    @Test
    public void testDeleteEmployee() {
        EmployeeDTO employeeDTO = CritterFunctionalTest.createEmployeeDTO();
        employeeDTO.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO);

        // check if the employee disappears
        userController.deleteEmployee(newEmployee.getId());
        List<EmployeeDTO> retrievedEmployees = userController.getAllEmployees();
        Assertions.assertTrue(retrievedEmployees.size() == 0);
    }

    /**
     * When a schedule is deleted the scheduled employees and pets remains unaffected
     */
    @Test
    public void testDeleteSchedule() {
        ScheduleDTO sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));

        // check if the schedule disappears
        scheduleController.deleteSchedule(sched1.getId());
        List<ScheduleDTO> retrievedSchedules = scheduleController.getAllSchedules();
        Assertions.assertTrue(retrievedSchedules.size() == 0);

        // check if the pets exist
        List<PetDTO> retrievedPets = petController.getPets();
        Assertions.assertTrue(retrievedPets.size() > 0);

        // check if the employees exist
        List<EmployeeDTO> retrievedEmployees = userController.getAllEmployees();
        Assertions.assertTrue(retrievedEmployees.size() > 0);
    }

    public ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        List<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> CritterFunctionalTest.createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e).getId();
                }).collect(Collectors.toList());
        CustomerDTO cust = userController.saveCustomer(CritterFunctionalTest.createCustomerDTO());
        List<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> CritterFunctionalTest.createPetDTO())
                .map(p -> {
                    p.setOwnerId(cust.getId());
                    return petController.savePet(p).getId();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(CritterFunctionalTest.createScheduleDTO(petIds, employeeIds, date, activities));
    }

}
