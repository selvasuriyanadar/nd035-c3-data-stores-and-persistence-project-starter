package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleModel, Long> {

    @Query("select a from ScheduleModel a where a.id in (select distinct a.id from ScheduleModel a join a.pets p where p.id = ?1)")
    public List<ScheduleModel> fetchByPetId(long petId);

    @Query("select a from ScheduleModel a where a.id in (select distinct a.id from ScheduleModel a join a.employees e where e.id = ?1)")
    public List<ScheduleModel> fetchByEmployeeId(long employeeId);

    @Query("select a from ScheduleModel a where a.id in (select distinct a.id from ScheduleModel a join a.pets p join p.owner o where o.id = ?1)")
    public List<ScheduleModel> fetchByCustomerId(long customerId);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p where p.id = ?1) then true else false end")
    public boolean existsByPetId(long petId);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.employees e where e.id = ?1) then true else false end")
    public boolean existsByEmployeeId(long employeeId);

    @Query("select case when exists(select distinct a.id from ScheduleModel a join a.pets p join p.owner o where o.id = ?1) then true else false end")
    public boolean existsByCustomerId(long customerId);

}
