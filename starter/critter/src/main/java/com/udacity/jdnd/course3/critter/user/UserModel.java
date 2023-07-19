package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;

import java.util.List;

/**
 * Polymorphic queries are not needed in this scenario, thus MappedSuperClass is used.
 * This creates separate table for each sub classes,
 * with the super class fields also present in each of those tables.
 */
@MappedSuperclass
public abstract class UserModel {

    @JsonView({EmployeeViews.Public.class, CustomerViews.Public.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonView({EmployeeViews.Public.class, CustomerViews.Public.class})
    @NotEmpty(message = "Name is required.")
    @Nationalized
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
