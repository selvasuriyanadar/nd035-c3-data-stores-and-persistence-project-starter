package com.udacity.jdnd.course3.critter.user;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

import java.util.List;

/**
 * Polymorphic queries are not needed in this scenario, thus MappedSuperClass is used.
 * This creates separate table for each sub classes,
 * with the super class fields also present in each of those tables.
 */
@MappedSuperclass
public abstract class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
