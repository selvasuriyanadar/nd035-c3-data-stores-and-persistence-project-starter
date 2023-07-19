package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * A example list of pet type metadata that could be included on a request to create a pet.
 */
public enum PetType {

    CAT(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.SHAVING)),

    DOG(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.WALKING, EmployeeSkill.SHAVING)),

    LIZARD(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.WATERING)),

    BIRD(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.WATERING, EmployeeSkill.PLAYING_OUT_OF_CAGE)),

    FISH(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.CHANGING_WATER)),

    SNAKE(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING, EmployeeSkill.WATERING, EmployeeSkill.BEDDING)),

    OTHER(Stream.of(EmployeeSkill.PETTING, EmployeeSkill.FEEDING, EmployeeSkill.MEDICATING));

    private Set<EmployeeSkill> releventActivities;

    PetType(Stream<EmployeeSkill> releventActivities) {
        this.releventActivities = releventActivities.collect(Collectors.toCollection(HashSet::new));
    }

    public Set<EmployeeSkill> getReleventActivities() {
        return new HashSet<>(this.releventActivities);
    }

}
