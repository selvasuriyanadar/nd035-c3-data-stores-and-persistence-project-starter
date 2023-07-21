package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.ScheduleModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
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

    @JsonIgnore
    @ManyToMany(mappedBy = "employees")
    private Set<ScheduleModel> schedules;

}
