package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import jakarta.persistence.EntityManager;

@Component
public class LongToPetConverter
  implements Converter<Long, PetModel> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PetRepository petRepository;

    @Override
    public PetModel convert(Long id) {
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet Not Found.");
        }

        return entityManager.getReference(PetModel.class, id);
    }
}
