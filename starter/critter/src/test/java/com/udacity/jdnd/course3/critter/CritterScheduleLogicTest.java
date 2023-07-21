package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.user.*;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.pet.PetTypeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleModel;
import com.udacity.jdnd.course3.critter.schedule.ScheduleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CritterScheduleLogicTest {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EntityManager entityManager;

    /*@MockBean
    private PetRepository petRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        given(petRepository.save(any())).willCallRealMethod();
        given(customerRepository.save(any())).willCallRealMethod();
    }*/

    private static class ScheduleData {
        public CustomerModel c1;
        public CustomerModel c2;
        public CustomerModel c3;

        public PetModel p1;
        public PetModel p2;
        public PetModel p3;
        public PetModel p4;
        public PetModel p5;
        public PetModel p6;
        public PetModel p7;
        public PetModel p8;
        public PetModel p9;

        public Stream<PetModel> streamAllPets() {
            return Stream.of(p1, p2, p3, p4, p5, p6, p7, p8, p9);
        }

        public EmployeeModel e1;
        public EmployeeModel e2;
        public EmployeeModel e3;
        public EmployeeModel e4;
        public EmployeeModel e5;

        public Stream<EmployeeModel> streamAllEmployees() {
            return Stream.of(e1, e2, e3, e4, e5);
        }
    }

    private static CustomerModel createCustomer(String name, String phoneNumber) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setName("Jerry");
        customerModel.setPhoneNumber("1237656780");
        return customerModel;
    }

    private static PetModel createPet(EntityManager entityManager, CustomerModel customerModel, PetType petType, String name) {
        PetModel petModel = new PetModel();
        petModel.setType(entityManager.getReference(PetTypeModel.class, petType));
        petModel.setName(name);
        petModel.setOwner(customerModel);
        return petModel;
    }

    private static EmployeeModel createEmployee(String name, Stream<EmployeeSkill> skills, Stream<DayOfWeek> daysAvailable) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setName(name);
        employeeModel.setSkills(skills.collect(Collectors.toSet()));
        employeeModel.setDaysAvailable(daysAvailable.collect(Collectors.toSet()));
        return employeeModel;
    }

    private static ScheduleModel createSchedule(Stream<PetModel> pets, Stream<EmployeeModel> employees, Stream<EmployeeSkill> skills, LocalDate date) {
        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setPets(pets.collect(Collectors.toSet()));
        scheduleModel.setEmployees(employees.collect(Collectors.toSet()));
        scheduleModel.setActivities(skills.collect(Collectors.toSet()));
        scheduleModel.setDate(date);
        return scheduleModel;
    }

    public ScheduleData createSample1() {
        ScheduleData scheduleData = new ScheduleData();

        scheduleData.c1 = userService.saveCustomer(createCustomer("Jerry", "1237656780"));
        scheduleData.c2 = userService.saveCustomer(createCustomer("Sam", "428 765-9780"));
        scheduleData.c3 = userService.saveCustomer(createCustomer("Toby", "+980 1934676780"));

        scheduleData.p1 = petService.savePet(createPet(entityManager, scheduleData.c1, PetType.CAT, "Tom"));
        scheduleData.p2 = petService.savePet(createPet(entityManager, scheduleData.c1, PetType.DOG, "Puppy"));

        scheduleData.p3 = petService.savePet(createPet(entityManager, scheduleData.c2, PetType.LIZARD, "Lizo"));
        scheduleData.p4 = petService.savePet(createPet(entityManager, scheduleData.c2, PetType.BIRD, "Twitty"));
        scheduleData.p5 = petService.savePet(createPet(entityManager, scheduleData.c2, PetType.DOG, "Cute Puppy"));

        scheduleData.p6 = petService.savePet(createPet(entityManager, scheduleData.c3, PetType.LIZARD, "Stupid"));
        scheduleData.p7 = petService.savePet(createPet(entityManager, scheduleData.c3, PetType.SNAKE, "Killer"));
        scheduleData.p8 = petService.savePet(createPet(entityManager, scheduleData.c3, PetType.DOG, "Guard"));
        scheduleData.p9 = petService.savePet(createPet(entityManager, scheduleData.c3, PetType.FISH, "Devil"));

        scheduleData.e1 = userService.saveEmployee(createEmployee("Mike", Stream.of(EmployeeSkill.PETTING, EmployeeSkill.WALKING, EmployeeSkill.WATERING), Stream.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)));
        scheduleData.e2 = userService.saveEmployee(createEmployee("John", Stream.of(EmployeeSkill.PETTING, EmployeeSkill.SHAVING, EmployeeSkill.PLAYING_OUT_OF_CAGE), Stream.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY)));
        scheduleData.e3 = userService.saveEmployee(createEmployee("Michael", Stream.of(EmployeeSkill.FEEDING, EmployeeSkill.WATERING, EmployeeSkill.PLAYING_OUT_OF_CAGE), Stream.of(DayOfWeek.MONDAY, DayOfWeek.SUNDAY, DayOfWeek.SATURDAY)));
        scheduleData.e4 = userService.saveEmployee(createEmployee("Robin", Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING, EmployeeSkill.WATERING, EmployeeSkill.BEDDING), Stream.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY)));
        scheduleData.e5 = userService.saveEmployee(createEmployee("Mary", Stream.of(EmployeeSkill.FEEDING, EmployeeSkill.BEDDING, EmployeeSkill.CHANGING_WATER), Stream.of(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY, DayOfWeek.FRIDAY, DayOfWeek.TUESDAY)));

        return scheduleData;
    }

    @Test
    public void testCreateSchedule() {
        ScheduleData d = createSample1();

        // successfully creating a schedule
        Assertions.assertDoesNotThrow(() -> scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3),
                    Stream.of(d.e1, d.e2, d.e4),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20)))); // WEDNESDAY

        // adding a pet that does not fit the scheduled activities
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3, d.p9),
                    Stream.of(d.e1, d.e2, d.e4),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20)))); // WEDNESDAY
        Assertions.assertTrue(exception.getMessage().contains("Pet " + d.p9.getName() + " has no relevent activities for the schedule."));

        // adding an employee that does not fit the scheduled date
        exception = Assertions.assertThrows(IllegalStateException.class, () -> scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3),
                    Stream.of(d.e1, d.e2, d.e3, d.e4),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20)))); // WEDNESDAY
        Assertions.assertTrue(exception.getMessage().contains("Employee " + d.e3.getName() + " is not available on the day of the schedule."));

        // adding an employee that does not fit the scheduled activities
        exception = Assertions.assertThrows(IllegalStateException.class, () -> scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3),
                    Stream.of(d.e1, d.e2, d.e4, d.e5),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20)))); // WEDNESDAY
        Assertions.assertTrue(exception.getMessage().contains("Employee " + d.e5.getName() + " does not have the required skills for the schedule."));
    }

    @Test
    public void testPetUpdate() {
        ScheduleData d = createSample1();

        ScheduleModel scheduleModel = scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3),
                    Stream.of(d.e1, d.e2, d.e4),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20))); // WEDNESDAY

        // testing that pet type can be updated
        d.p1.setType(entityManager.getReference(PetTypeModel.class, PetType.SNAKE));
        petService.savePet(d.p1);

        // testing that if a pet or its owner with schedules is deleted an exception is thrown

        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> petService.deletePet(d.p1));
        Assertions.assertTrue(exception.getMessage().contains("The pet is scheduled for some activities."));

        exception = Assertions.assertThrows(IllegalStateException.class, () -> userService.deleteCustomer(d.p1.getOwner()));
        Assertions.assertTrue(exception.getMessage().contains("The customer has some pets scheduled for some activities."));

        // testing that if the new pet type does not fit with its schedules then an exception is thrown
        d.p1.setType(entityManager.getReference(PetTypeModel.class, PetType.FISH));
        exception = Assertions.assertThrows(IllegalStateException.class, () -> petService.savePet(d.p1));
        Assertions.assertTrue(exception.getMessage().contains("The pet is scheduled for some activities."));

        // testing that old schedules does not affect the update or delete of the pet

        d.p1.setType(entityManager.getReference(PetTypeModel.class, PetType.CAT));
        petService.savePet(d.p1);
        scheduleModel.setDate(LocalDate.of(2022, 9, 21)); // WEDNESDAY
        scheduleService.saveSchedule(scheduleModel);

        d.p1.setType(entityManager.getReference(PetTypeModel.class, PetType.FISH));
        petService.savePet(d.p1);

        CritterFunctionalTest.flushAndClearSession(entityManager);

        PetModel pet1 = entityManager.getReference(PetModel.class, d.p1.getId());
        CustomerModel pet1Owner = pet1.getOwner();
        petService.deletePet(pet1);
        userService.deleteCustomer(pet1Owner);
    }

    @Test
    public void testEmployeeUpdate() {
        ScheduleData d = createSample1();

        ScheduleModel scheduleModel = scheduleService.saveSchedule(createSchedule(Stream.of(d.p1, d.p2, d.p3),
                    Stream.of(d.e1, d.e2, d.e4),
                    Stream.of(EmployeeSkill.SHAVING, EmployeeSkill.WALKING, EmployeeSkill.WATERING),
                    LocalDate.of(2023, 9, 20))); // WEDNESDAY

        // testing that employee can be updated
        d.e4.setSkills(Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING, EmployeeSkill.WATERING).collect(Collectors.toSet()));
        d.e4.setDaysAvailable(Stream.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY).collect(Collectors.toSet()));
        userService.saveEmployee(d.e4);

        // testing that if a employee with schedules is deleted an exception is thrown

        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> userService.deleteEmployee(d.e4));
        Assertions.assertTrue(exception.getMessage().contains("The employee has been scheduled for some activities."));

        // testing that if the new skills does not fit with its schedules then an exception is thrown
        d.e4.setSkills(Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING).collect(Collectors.toSet()));
        exception = Assertions.assertThrows(IllegalStateException.class, () -> userService.saveEmployee(d.e4));
        Assertions.assertTrue(exception.getMessage().contains("The employee is scheduled for some activities."));

        // testing that if the new available days does not fit with its schedules then an exception is thrown
        d.e4.setSkills(Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING, EmployeeSkill.WATERING).collect(Collectors.toSet()));
        d.e4.setDaysAvailable(Stream.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY).collect(Collectors.toSet()));
        exception = Assertions.assertThrows(IllegalStateException.class, () -> userService.saveEmployee(d.e4));
        Assertions.assertTrue(exception.getMessage().contains("The employee is scheduled for some activities."));

        // testing that old schedules does not affect the update or delete of the employee

        d.e4.setSkills(Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING, EmployeeSkill.WATERING).collect(Collectors.toSet()));
        d.e4.setDaysAvailable(Stream.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY).collect(Collectors.toSet()));
        userService.saveEmployee(d.e4);
        scheduleModel.setDate(LocalDate.of(2022, 9, 21)); // WEDNESDAY
        scheduleService.saveSchedule(scheduleModel);

        d.e4.setSkills(Stream.of(EmployeeSkill.MEDICATING, EmployeeSkill.FEEDING).collect(Collectors.toSet()));
        d.e4.setDaysAvailable(Stream.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY).collect(Collectors.toSet()));
        userService.saveEmployee(d.e4);

        CritterFunctionalTest.flushAndClearSession(entityManager);

        EmployeeModel employee4 = entityManager.getReference(EmployeeModel.class, d.e4.getId());
        userService.deleteEmployee(employee4);
    }

}
