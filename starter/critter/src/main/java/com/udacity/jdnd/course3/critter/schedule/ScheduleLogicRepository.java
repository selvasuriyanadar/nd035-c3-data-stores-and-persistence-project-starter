package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.stream.Stream;
import java.util.*;

@Repository
public interface ScheduleLogicRepository extends JpaRepository<ScheduleModel, Long> {

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p where p.id = ?1 and a.date >= ?2) then true else false end")
    public boolean existsByPetIdAndGreaterThanOrEqualToDate(long petId, LocalDate date);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.employees e where e.id = ?1 and a.date >= ?2) then true else false end")
    public boolean existsByEmployeeIdAndGreaterThanOrEqualToDate(long employeeId, LocalDate date);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p join p.owner o where o.id = ?1 and a.date >= ?2) then true else false end")
    public boolean existsByCustomerIdAndGreaterThanOrEqualToDate(long customerId, LocalDate date);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p join p.type pt join a.activities activity where pt.type = ?1 and activity not in ?3 and a.date >= ?2 and a.id not in (select distinct a.id from ScheduleModel a join a.pets p join p.type pt join a.activities activity where pt.type = ?1 and activity in ?3 and a.date >= ?2)) then true else false end")
    public boolean existsByPetTypeAndGreaterThanOrEqualToDateAndActivitiesNotReleventToPet(PetType type, LocalDate date, Set<EmployeeSkill> releventActivities);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p join p.type pt join a.activities activity where p.id = ?1 and activity not in ?3 and a.date >= ?2 and a.id not in (select distinct a.id from ScheduleModel a join a.pets p join p.type pt join a.activities activity where p.id = ?1 and activity in ?3 and a.date >= ?2)) then true else false end")
    public boolean existsByPetIdAndGreaterThanOrEqualToDateAndActivitiesNotReleventToPet(long petId, LocalDate date, Set<EmployeeSkill> releventActivities);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.employees e join a.activities activity where e.id = ?1 and activity not in ?3 and a.date >= ?2 and a.id not in (select distinct a.id from ScheduleModel a join a.employees e join a.activities activity where e.id = ?1 and activity in ?3 and a.date >= ?2)) then true else false end")
    public boolean existsByEmployeeIdAndGreaterThanOrEqualToDateAndActivitiesNotReleventToEmployee(long employeeId, LocalDate date, Set<EmployeeSkill> skills);

    @Query("select distinct a.date from ScheduleModel a join a.employees e where e.id = ?1 and a.date >= ?2")
    public Stream<LocalDate> fetchDateByEmployeeIdAndGreaterThanOrEqualToDate(long employeeId, LocalDate date);

}
