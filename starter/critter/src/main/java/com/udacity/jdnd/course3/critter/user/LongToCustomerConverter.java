package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import jakarta.persistence.EntityManager;

@Component
public class LongToCustomerConverter
  implements Converter<Long, CustomerModel> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerModel convert(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer Not Found.");
        }

        return entityManager.getReference(CustomerModel.class, id);
    }
}
