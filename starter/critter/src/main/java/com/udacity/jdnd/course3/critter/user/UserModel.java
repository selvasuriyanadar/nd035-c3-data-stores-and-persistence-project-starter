package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Polymorphic queries are not needed in this scenario, thus MappedSuperClass is used.
 * This creates separate table for each sub classes,
 * with the super class fields also present in each of those tables.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class UserModel {

    @EqualsAndHashCode.Include
    @JsonView({EmployeeViews.Public.class, CustomerViews.Public.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonView({EmployeeViews.Public.class, CustomerViews.Public.class})
    @NotEmpty(message = "Name is required.")
    @Nationalized
    private String name;

}
