package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleModel model = BeanUtil.transferWithIgnoreFields(scheduleDTO, new ScheduleModel(), "id");
        if (scheduleDTO.getEmployeeIds() != null) {
            model.setEmployees(scheduleDTO.getEmployeeIds().stream().distinct().filter(employeeId -> (employeeId != null)).map(employeeId -> {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.setId(employeeId);
                return employeeModel;
            }).toList());
        }
        if (scheduleDTO.getPetIds() != null) {
            model.setPets(scheduleDTO.getPetIds().stream().distinct().filter(petId -> (petId != null)).map(petId -> {
                PetModel petModel = new PetModel();
                petModel.setId(petId);
                return petModel;
            }).toList());
        }
        ScheduleDTO scheduleDTOResponse = BeanUtil.transfer(scheduleService.createSchedule(model), new ScheduleDTO());
        if (model.getEmployees() != null) {
            scheduleDTOResponse.setEmployeeIds(model.getEmployees().stream().map(employee -> employee.getId()).toList());
        }
        if (model.getPets() != null) {
            scheduleDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
        }
        return scheduleDTOResponse;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(model -> {
            ScheduleDTO scheduleDTOResponse = BeanUtil.transfer(model, new ScheduleDTO());
            if (model.getEmployees() != null) {
                scheduleDTOResponse.setEmployeeIds(model.getEmployees().stream().map(employee -> employee.getId()).toList());
            }
            if (model.getPets() != null) {
                scheduleDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
            }
            return scheduleDTOResponse;
        }).toList();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleRepository.fetchByPetId(petId).stream().map(model -> {
            ScheduleDTO scheduleDTOResponse = BeanUtil.transfer(model, new ScheduleDTO());
            if (model.getEmployees() != null) {
                scheduleDTOResponse.setEmployeeIds(model.getEmployees().stream().map(employee -> employee.getId()).toList());
            }
            if (model.getPets() != null) {
                scheduleDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
            }
            return scheduleDTOResponse;
        }).toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleRepository.fetchByEmployeeId(employeeId).stream().map(model -> {
            ScheduleDTO scheduleDTOResponse = BeanUtil.transfer(model, new ScheduleDTO());
            if (model.getEmployees() != null) {
                scheduleDTOResponse.setEmployeeIds(model.getEmployees().stream().map(employee -> employee.getId()).toList());
            }
            if (model.getPets() != null) {
                scheduleDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
            }
            return scheduleDTOResponse;
        }).toList();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleRepository.fetchByCustomerId(customerId).stream().map(model -> {
            ScheduleDTO scheduleDTOResponse = BeanUtil.transfer(model, new ScheduleDTO());
            if (model.getEmployees() != null) {
                scheduleDTOResponse.setEmployeeIds(model.getEmployees().stream().map(employee -> employee.getId()).toList());
            }
            if (model.getPets() != null) {
                scheduleDTOResponse.setPetIds(model.getPets().stream().map(pet -> pet.getId()).toList());
            }
            return scheduleDTOResponse;
        }).toList();
    }
}
