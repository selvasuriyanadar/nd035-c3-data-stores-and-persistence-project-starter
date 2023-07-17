package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetModel;

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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<PetModel> pets;

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

}
