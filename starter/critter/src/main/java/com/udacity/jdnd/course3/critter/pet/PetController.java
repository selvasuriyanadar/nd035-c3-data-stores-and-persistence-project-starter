package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;

import java.util.*;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetModel model = BeanUtil.transfer(petDTO, new PetModel());
        if (!customerRepository.existsById(petDTO.getOwnerId())) {
            throw new IllegalArgumentException("Customer Not Found.");
        }
        model.setOwner(entityManager.getReference(CustomerModel.class, petDTO.getOwnerId()));
        PetDTO petDTOResponse = BeanUtil.transfer(petService.savePet(model), new PetDTO());
        petDTOResponse.setOwnerId(model.getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<PetModel> modelOpt = petRepository.findById(petId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Pet Not Found.");
        }
        PetDTO petDTOResponse = BeanUtil.transfer(modelOpt.get(), new PetDTO());
        petDTOResponse.setOwnerId(modelOpt.get().getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petRepository.findAll().stream().map(model -> {
            PetDTO petDTOResponse = BeanUtil.transfer(model, new PetDTO());
            petDTOResponse.setOwnerId(model.getOwner().getId());
            return petDTOResponse;
        }).toList();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petRepository.fetchByOwnerId(ownerId).stream().map(model -> {
            PetDTO petDTOResponse = BeanUtil.transfer(model, new PetDTO());
            petDTOResponse.setOwnerId(model.getOwner().getId());
            return petDTOResponse;
        }).toList();
    }
}
