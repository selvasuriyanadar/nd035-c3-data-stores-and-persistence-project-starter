package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.schedule.ScheduleModel;
import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.LocalDate;
import java.util.*;

public class ScheduleLogic {

    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public ScheduleLogic(ScheduleModel model) {
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

    public void schedulePet(PetModel model) {
    }

    public void scheduleEmployee(EmployeeModel model) {
        if (!model.getDaysAvailable().contains(this.date.getDayOfWeek())) {
            throw new IllegalStateException("Employee " + model.getName() + " is not available on the day of the schedule.");
        }
        if (!model.getSkills().containsAll(this.activities)) {
            throw new IllegalStateException("Employee " + model.getName() + " does not have the required skills for the schedule.");
        }
    }

}
