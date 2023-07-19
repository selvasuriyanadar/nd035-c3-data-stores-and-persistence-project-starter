package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private ScheduleModel complete(ScheduleModel scheduleModel) {
        scheduleModel.setEmployeeIds(scheduleRepository.fetchEmployeeIdByScheduleId(scheduleModel.getId()));
        scheduleModel.setPetIds(scheduleRepository.fetchPetIdByScheduleId(scheduleModel.getId()));
        return scheduleModel;
    }

    @JsonView(ScheduleViews.Public.class)
    @PostMapping
    public ScheduleModel createSchedule(@RequestBody ScheduleModel scheduleModel) {
        return complete(scheduleService.createSchedule(scheduleModel));
    }

    @JsonView(ScheduleViews.Public.class)
    @GetMapping
    public List<ScheduleModel> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleModel -> complete(scheduleModel)).toList();
    }

    @JsonView(ScheduleViews.Public.class)
    @GetMapping("/pet/{petId}")
    public List<ScheduleModel> getScheduleForPet(@PathVariable long petId) {
        return scheduleRepository.fetchByPetId(petId).stream().map(scheduleModel -> complete(scheduleModel)).toList();
    }

    @JsonView(ScheduleViews.Public.class)
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleModel> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleRepository.fetchByEmployeeId(employeeId).stream().map(scheduleModel -> complete(scheduleModel)).toList();
    }

    @JsonView(ScheduleViews.Public.class)
    @GetMapping("/customer/{customerId}")
    public List<ScheduleModel> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleRepository.fetchByCustomerId(customerId).stream().map(scheduleModel -> complete(scheduleModel)).toList();
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable("scheduleId") ScheduleModel scheduleModel) {
        scheduleService.deleteSchedule(scheduleModel);
    }

}
