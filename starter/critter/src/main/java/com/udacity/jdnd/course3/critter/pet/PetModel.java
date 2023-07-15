package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class PetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    @Nationalized
    private String name;
    @OneToOne
    private CustomerModel owner;
    private LocalDate birthDate;
    @Column(length = 1000)
    private String notes;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerModel getOwner() {
        return owner;
    }

    public void setOwner(CustomerModel owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
