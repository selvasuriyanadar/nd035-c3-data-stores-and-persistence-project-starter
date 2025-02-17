package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.CustomerModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PetTypeModel {

    @EqualsAndHashCode.Include
    @NotNull(message = "Type is required.")
    @Enumerated(EnumType.STRING)
    @Id
    private PetType type;

    @NotEmpty(message = "Relevent Activities are required.")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<@NotNull EmployeeSkill> releventActivities;

    public PetTypeModel(PetType type) {
        this.type = type;
        this.releventActivities = type.getReleventActivities();
    }

}
