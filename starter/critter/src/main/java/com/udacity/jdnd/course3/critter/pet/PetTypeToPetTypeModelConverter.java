package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import jakarta.persistence.EntityManager;

@Component
public class PetTypeToPetTypeModelConverter
  implements Converter<PetType, PetTypeModel> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public PetTypeModel convert(PetType id) {
        if (!petTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet Type Not Found.");
        }

        return entityManager.getReference(PetTypeModel.class, id);
    }
}
