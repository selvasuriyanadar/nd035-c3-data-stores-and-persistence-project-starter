package com.udacity.jdnd.course3.critter.user;

import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class EmployeeModel extends UserModel {

    @NotEmpty(message = "Skills are required.")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<@NotNull EmployeeSkill> skills;

    @NotEmpty(message = "Days Available are required.")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<@NotNull DayOfWeek> daysAvailable;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

}
