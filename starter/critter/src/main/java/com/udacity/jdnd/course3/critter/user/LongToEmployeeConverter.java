package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import jakarta.persistence.EntityManager;

@Component
public class LongToEmployeeConverter
  implements Converter<Long, EmployeeModel> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeModel convert(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee Not Found.");
        }

        return entityManager.getReference(EmployeeModel.class, id);
    }
}
