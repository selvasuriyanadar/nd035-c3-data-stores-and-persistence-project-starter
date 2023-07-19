package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class ScheduleModel {

    @JsonView(ScheduleViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty(value = "employeeIds", access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Employees in a schedule shall not be empty.")
    @ManyToMany
    private Set<@NotNull EmployeeModel> employees;

    @JsonView(ScheduleViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private Set<Long> employeeIds;

    @JsonProperty(value = "petIds", access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Pets in a schedule shall not be empty.")
    @ManyToMany
    private Set<@NotNull PetModel> pets;

    @JsonView(ScheduleViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private Set<Long> petIds;

    @JsonView(ScheduleViews.Public.class)
    @NotNull(message = "Scheduled Date is required.")
    private LocalDate date;

    @JsonView(ScheduleViews.Public.class)
    @NotEmpty(message = "Activities the Employees are expected to carry out are required.")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<@NotNull EmployeeSkill> activities;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Set<EmployeeModel> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeModel> employees) {
        this.employees = employees;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public Set<PetModel> getPets() {
        return pets;
    }

    public void setPets(Set<PetModel> pets) {
        this.pets = pets;
    }

    public void setPetIds(Set<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
