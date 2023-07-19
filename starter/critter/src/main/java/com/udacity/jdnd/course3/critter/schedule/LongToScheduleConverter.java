package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import jakarta.persistence.EntityManager;

@Component
public class LongToScheduleConverter
  implements Converter<Long, ScheduleModel> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ScheduleModel convert(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("Schedule Not Found.");
        }

        return entityManager.getReference(ScheduleModel.class, id);
    }
}
