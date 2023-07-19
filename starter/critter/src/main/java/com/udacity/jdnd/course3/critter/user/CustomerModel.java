package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CustomerModel extends UserModel {

    @NotNull(message = "Phone Number is required.")
    private String phoneNumber;

    @Column(length = 1000)
    private String notes;

    @JsonProperty(value = "petIds", access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<@NotNull PetModel> pets;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private List<Long> petIds;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<PetModel> getPets() {
        return pets;
    }

    public void setPets(List<PetModel> pets) {
        this.pets = pets;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

}
