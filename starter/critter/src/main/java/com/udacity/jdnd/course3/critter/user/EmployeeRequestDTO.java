package com.udacity.jdnd.course3.critter.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Data
@NoArgsConstructor
public class EmployeeRequestDTO {
    @NotEmpty
    private Set<@NotNull EmployeeSkill> skills;
    @NotNull
    private LocalDate date;
}
