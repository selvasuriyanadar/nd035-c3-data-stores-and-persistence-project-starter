package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class EmployeeModel extends UserModel {

    @JsonView(EmployeeViews.Public.class)
    @NotEmpty(message = "Skills are required.")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<@NotNull EmployeeSkill> skills;

    @JsonView(EmployeeViews.Public.class)
    @NotEmpty(message = "Days Available are required.")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<@NotNull DayOfWeek> daysAvailable;

    @Override
    public boolean equals(Object obj) {
        return BeanUtil.checkEqualsById(this, obj, EmployeeModel::getId);
    }

    @Override
    public int hashCode() {
        return BeanUtil.hashById(getId());
    }

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
