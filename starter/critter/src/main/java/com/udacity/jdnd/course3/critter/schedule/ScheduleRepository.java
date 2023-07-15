package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleModel, Long> {
}
