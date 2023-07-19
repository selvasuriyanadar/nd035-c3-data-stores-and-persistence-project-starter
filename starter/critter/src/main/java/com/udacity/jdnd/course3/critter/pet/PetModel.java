package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class PetModel {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Type is required.")
    @Enumerated(EnumType.STRING)
    private PetType type;

    @NotEmpty(message = "Name is required.")
    @Nationalized
    private String name;

    @JsonProperty(value = "ownerId", access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Owner is required.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private CustomerModel owner;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private long ownerId;

    private LocalDate birthDate;

    @Column(length = 1000)
    private String notes;

    @Override
    public boolean equals(Object obj) {
        return BeanUtil.checkEqualsById(this, obj, PetModel::getId);
    }

    @Override
    public int hashCode() {
        return BeanUtil.hashById(getId());
    }

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

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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
