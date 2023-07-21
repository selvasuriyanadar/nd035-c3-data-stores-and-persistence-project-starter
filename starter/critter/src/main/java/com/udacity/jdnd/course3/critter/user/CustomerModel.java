package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class CustomerModel extends UserModel {

    @JsonView(CustomerViews.Public.class)
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Invalid Phone Number.")
    @NotEmpty(message = "Phone Number is required.")
    private String phoneNumber;

    @JsonView(CustomerViews.Public.class)
    @Column(length = 1000)
    private String notes;

    @JsonProperty(value = "petIds", access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<@NotNull PetModel> pets;

    @JsonView(CustomerViews.Public.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private Set<Long> petIds;

}
