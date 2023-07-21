package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.ScheduleModel;
import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.pet.PetTypeModel;

import com.udacity.jdnd.course3.critter.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * This class contains the various requirements for the schedule module in one place
 */

@Service
public class ScheduleLogic {

    @Autowired
    private ScheduleLogicRepository scheduleLogicRepository;

    /**
     * An employee can only be scheduled if the employee has any of the skills required for the schedule
     * An employee can only be scheduled if the employee is available on the day of the schedule
     * A pet can only be scheduled if any of the scheduled activities are relevent for it
     */

    private static class Scheduler {

        private LocalDate date;
        private Set<EmployeeSkill> activities;

        public Scheduler(ScheduleModel model) {
            this.date = model.getDate();
            this.activities = model.getActivities();
        }

        public void schedule(ScheduleModel model) {
            for (PetModel petModel : model.getPets()) {
                schedulePet(petModel);
            }
            for (EmployeeModel employeeModel : model.getEmployees()) {
                scheduleEmployee(employeeModel);
            }
        }

        private void schedulePet(PetModel model) {
            if (!CollectionUtil.containsAny(this.activities, model.getType().getReleventActivities())) {
                throw new IllegalStateException("Pet " + model.getName() + " has no relevent activities for the schedule.");
            }
        }

        private void scheduleEmployee(EmployeeModel model) {
            if (!model.getDaysAvailable().contains(this.date.getDayOfWeek())) {
                throw new IllegalStateException("Employee " + model.getName() + " is not available on the day of the schedule.");
            }
            if (!CollectionUtil.containsAny(this.activities, model.getSkills())) {
                throw new IllegalStateException("Employee " + model.getName() + " does not have the required skills for the schedule.");
            }
        }

    }

    public void schedule(ScheduleModel model) {
        new Scheduler(model).schedule(model);
    }

    /**
     * When a pet type's relevent activities are modified it is important to make sure that
     * the pets of that type are still valid for all the current and future schedules for which those have been scheduled.
     * That is there should not be any current and future schedule of the pets under the pet type that no more contains
     * any relevent activities for it.
     */

    public void updatePetType(PetTypeModel model) {
        if (scheduleLogicRepository.existsByPetTypeAndGreaterThanOrEqualToDateAndActivitiesNotReleventToPet(model.getType(), LocalDate.now(), model.getReleventActivities())) {
            throw new IllegalStateException("The pets of this pet type are scheduled for some activities.");
        }
    }

    /**
     * When a pet's relevent activities are modified it is important to make sure that
     * it is still valid for all the current and future schedules for which it has been scheduled.
     * That is there should not be any current and future schedule of the pet that no more contains
     * any relevent activities for it.
     */

    public void updatePet(PetModel model) {
        if (scheduleLogicRepository.existsByPetIdAndGreaterThanOrEqualToDateAndActivitiesNotReleventToPet(model.getId(), LocalDate.now(), model.getType().getReleventActivities())) {
            throw new IllegalStateException("The pet is scheduled for some activities.");
        }
    }

    /**
     * When an employee's available days and skills are modified it is important to make sure
     * that it is still valid for all the current and future schedules for which it has been scheduled.
     * That is there should not be any current and future schedule of the employee that no more contains
     * any relevent activities for them.
     * Also there should not be any current and future schedule that does not falls in the available days
     * of the employee.
     */

    public void updateEmployee(EmployeeModel model) {
        if (scheduleLogicRepository.existsByEmployeeIdAndGreaterThanOrEqualToDateAndActivitiesNotReleventToEmployee(model.getId(), LocalDate.now(), model.getSkills())) {
            throw new IllegalStateException("The employee is scheduled for some activities.");
        }
        if (!scheduleLogicRepository.fetchDateByEmployeeIdAndGreaterThanOrEqualToDate(model.getId(), LocalDate.now()).filter(date -> !model.getDaysAvailable().contains(date.getDayOfWeek())).toList().isEmpty()) {
            throw new IllegalStateException("The employee is scheduled for some activities.");
        }
    }

    /**
     * When pets or employees or the owners of the pets are removed it has to be verified,
     * that there are no current and future schedules for those entities.
     */

    public void removePet(PetModel model) {
        if (scheduleLogicRepository.existsByPetIdAndGreaterThanOrEqualToDate(model.getId(), LocalDate.now())) {
            throw new IllegalStateException("The pet is scheduled for some activities.");
        }
    }

    public void removeEmployee(EmployeeModel model) {
        if (scheduleLogicRepository.existsByEmployeeIdAndGreaterThanOrEqualToDate(model.getId(), LocalDate.now())) {
            throw new IllegalStateException("The employee has been scheduled for some activities.");
        }
    }

    public void removeCustomer(CustomerModel model) {
        if (scheduleLogicRepository.existsByCustomerIdAndGreaterThanOrEqualToDate(model.getId(), LocalDate.now())) {
            throw new IllegalStateException("The customer has some pets scheduled for some activities.");
        }
    }

}
