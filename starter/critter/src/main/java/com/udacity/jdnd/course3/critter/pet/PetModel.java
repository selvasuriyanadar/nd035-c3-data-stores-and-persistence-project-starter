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
public class PetModel {

    @EqualsAndHashCode.Include
    @JsonView(PetViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Type is required.")
    @ManyToOne
    private PetTypeModel type;

    @JsonView(PetViews.Public.class)
    @JsonProperty(value = "type", access = JsonProperty.Access.READ_ONLY)
    @Transient
    private PetType typeEnum;

    @JsonView(PetViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private Set<EmployeeSkill> releventActivities;

    @JsonView(PetViews.Public.class)
    @NotEmpty(message = "Name is required.")
    @Nationalized
    private String name;

    @JsonProperty(value = "ownerId", access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Owner is required.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private CustomerModel owner;

    @JsonView(PetViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private long ownerId;

    @JsonView(PetViews.Public.class)
    private LocalDate birthDate;

    @JsonView(PetViews.Public.class)
    @Column(length = 1000)
    private String notes;

}
