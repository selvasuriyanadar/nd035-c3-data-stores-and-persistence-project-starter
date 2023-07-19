package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;

@Component
public class PetTypeDataLoader implements ApplicationRunner {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetService petService;

    public void run(ApplicationArguments args) {
        Arrays.stream(PetType.values())
            .filter(petType -> !petTypeRepository.existsById(petType))
            .forEach(petType -> petService.configurePetType(new PetTypeModel(petType)));
    }
}
