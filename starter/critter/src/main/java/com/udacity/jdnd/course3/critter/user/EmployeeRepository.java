package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.util.*;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    @Query("select a from EmployeeModel a where ?2 member of a.daysAvailable and a.id in (select distinct a.id from EmployeeModel a join a.skills skill where skill in ?1)")
    List<EmployeeModel> fetchBySkillsAndAvailableDay(Set<EmployeeSkill> employeeSkill, DayOfWeek dayOfWeek);

}
