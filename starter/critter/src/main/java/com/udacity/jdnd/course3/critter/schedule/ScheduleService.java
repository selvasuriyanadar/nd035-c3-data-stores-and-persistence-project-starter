package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleModel createSchedule(ScheduleModel scheduleModel) {
        return scheduleRepository.save(scheduleModel);
    }

}
